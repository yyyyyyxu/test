package com.yanxu.book.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yanxu.book.entity.LoginFailureHistory;
import com.yanxu.book.entity.Setting;
import com.yanxu.book.entity.User;
import com.yanxu.book.mapper.SettingMapper;
import com.yanxu.book.mapper.UserLoginFailureHistoryMapper;
import com.yanxu.book.mapper.UserMapper;
import com.yanxu.book.settingEnum.ParameterCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import java.util.Date;

public class DaoAuthenticationProvider extends AbstractBookUserDetailsAuthenticationProvider {

    @Autowired
    UserLoginFailureHistoryMapper mapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    SettingMapper settingMapper;

    private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private volatile String userNotFoundEncodedPassword;
    private UserDetailsService userDetailsService;
    private UserDetailsPasswordService userDetailsPasswordService;


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        User user=userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUserName,authentication.getPrincipal()));
        int faultTime=user.getFaultTime();
        Setting setting=settingMapper.selectOne(new QueryWrapper<Setting>().lambda().eq(Setting::getParameterCode, ParameterCodeEnum.REFUSED_TOLOGIN.getParameterCode())
                .eq(Setting::getParameterName,ParameterCodeEnum.REFUSED_TOLOGIN.getParameterName()));
        int refusedToLogin=Integer.parseInt(setting.getParameterValue());

        if (authentication.getCredentials() == null) {
            if (faultTime>=refusedToLogin){
                throw new LockedException("失败次数过多已锁定");
            }
            this.logger.debug("Authentication failed: no credentials provided");
            LoginFailureHistory loginFailureHistory = new LoginFailureHistory();
            loginFailureHistory.setUserName(userDetails.getUsername());
            loginFailureHistory.setCreatTime(new Date());
            mapper.insert(loginFailureHistory);

            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {



            String presentedPassword = authentication.getCredentials().toString();
            if (!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {

                if (faultTime+1>=refusedToLogin){
                    user.setFaultTime(user.getFaultTime()+1);
                    userMapper.update(user,new UpdateWrapper<User>().lambda().eq(User::getUserName,authentication.getPrincipal()));
                    LoginFailureHistory loginFailureHistory = new LoginFailureHistory();
                    loginFailureHistory.setUserName(userDetails.getUsername());
                    loginFailureHistory.setCreatTime(new Date());
                    mapper.insert(loginFailureHistory);
                    this.logger.debug("Authentication failed: password does not match stored value");
                    throw new LockedException("失败次数过多已锁定");
                }
                user.setFaultTime(user.getFaultTime()+1);
                userMapper.update(user,new UpdateWrapper<User>().lambda().eq(User::getUserName,authentication.getPrincipal()));
                LoginFailureHistory loginFailureHistory = new LoginFailureHistory();
                loginFailureHistory.setUserName(userDetails.getUsername());
                loginFailureHistory.setCreatTime(new Date());
                mapper.insert(loginFailureHistory);
                this.logger.debug("Authentication failed: password does not match stored value");
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "密码错误"));
            }else if (faultTime>=refusedToLogin){
                throw new LockedException("失败次数过多已锁定");
            }else {user.setFaultTime(0);
                userMapper.update(user,new UpdateWrapper<User>().lambda().eq(User::getUserName,authentication.getPrincipal()));
            }
        }
    }

    @Override
    protected void doAfterPropertiesSet() {
        Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
    }

    @Override
    protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        this.prepareTimingAttackProtection();

        try {
            UserDetails loadedUser = this.getUserDetailsService().loadUserByUsername(username);
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
            } else {
                return loadedUser;
            }
        } catch (UsernameNotFoundException var4) {
            this.mitigateAgainstTimingAttack(authentication);
            throw var4;
        } catch (InternalAuthenticationServiceException var5) {
            throw var5;
        } catch (Exception var6) {
            throw new InternalAuthenticationServiceException(var6.getMessage(), var6);
        }
    }

    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        boolean upgradeEncoding = this.userDetailsPasswordService != null && this.passwordEncoder.upgradeEncoding(user.getPassword());
        if (upgradeEncoding) {
            String presentedPassword = authentication.getCredentials().toString();
            String newPassword = this.passwordEncoder.encode(presentedPassword);
            user = this.userDetailsPasswordService.updatePassword(user, newPassword);
        }

        return super.createSuccessAuthentication(principal, authentication, user);
    }

    private void prepareTimingAttackProtection() {
        if (this.userNotFoundEncodedPassword == null) {
            this.userNotFoundEncodedPassword = this.passwordEncoder.encode("userNotFoundPassword");
        }

    }

    private void mitigateAgainstTimingAttack(UsernamePasswordAuthenticationToken authentication) {
        if (authentication.getCredentials() != null) {
            String presentedPassword = authentication.getCredentials().toString();
            this.passwordEncoder.matches(presentedPassword, this.userNotFoundEncodedPassword);
        }

    }


    protected PasswordEncoder getPasswordEncoder() {
        return this.passwordEncoder;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    protected UserDetailsService getUserDetailsService() {
        return this.userDetailsService;
    }

    public void setUserDetailsPasswordService(UserDetailsPasswordService userDetailsPasswordService) {
        this.userDetailsPasswordService = userDetailsPasswordService;
    }
}