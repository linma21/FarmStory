package kr.co.farmstory.config;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

// 전역설정
@Getter
@Setter
@Configuration
@EnableAspectJAutoProxy
public class RootConfig {
    @Autowired
    private BuildProperties buildProperties;

    @Bean
    public AppInfo appInfo(){
        String name = buildProperties.getName();
        String version = buildProperties.getVersion();
        return new AppInfo(name, version);
    }
}
