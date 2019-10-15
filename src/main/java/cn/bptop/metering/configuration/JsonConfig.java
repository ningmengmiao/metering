package cn.bptop.metering.configuration;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

public class JsonConfig extends WebMvcConfigurerAdapter
{
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
    {
    }
}