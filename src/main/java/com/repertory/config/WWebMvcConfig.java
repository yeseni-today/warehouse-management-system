package com.repertory.config;

import com.repertory.Application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.tiles2.dialect.TilesDialect;
import org.thymeleaf.extras.tiles2.spring4.web.configurer.ThymeleafTilesConfigurer;
import org.thymeleaf.extras.tiles2.spring4.web.view.ThymeleafTilesView;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;
import org.thymeleaf.templateresolver.UrlTemplateResolver;

/**
 * 该配置类 类似于mvc配置文件:spring_mvc.xml
 * Created by SXY on 2016/1/19.
 */
// @EnableWebMvc 用来导入mvc框架的自动化配置，使用前提是该类有@Configuration存在
/*@ComponentScan 扫描控制器组件，使用方式有两种:
* 1.指定具体类 例如@ComponentScan(basePackageClasses = HelloController.class)，或者 basePackageClasses = {HelloController.class,xxx.class,…}
* 2.指定基础包 例如本例使用的方法，或者数组形式@ComponentScan({"com.*.**","com.*.**"})
* 注：basePackages 关键字 可以选择性添加，默认会自动匹配到basePackages
* */
@Configuration
@EnableWebMvc
@Import({Factory.class,DataSourceConfig.class})
@ComponentScan(basePackageClasses = Application.class, includeFilters = @ComponentScan.Filter(Controller.class), useDefaultFilters = false)
public class WWebMvcConfig extends WebMvcConfigurerAdapter {

    //    声明页面的编码格式、类型
    private static final String CONTENTTYPE = "text/html; charset=UTF-8";

    private static final String RESOURCES_HANDLER = "/resources/";
    private static final String RESOURCES_LOCATION = RESOURCES_HANDLER + "**";


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(RESOURCES_LOCATION).addResourceLocations(RESOURCES_HANDLER);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


// tiles的配置文件路径
    @Bean
    public ThymeleafTilesConfigurer tilesConfigurer() {
        ThymeleafTilesConfigurer configurer = new ThymeleafTilesConfigurer();
        configurer.setDefinitions("/WEB-INF/tiles.xml");
        return configurer;
    }


    //    Thymeleaf框架配置
    @Bean
    public TemplateResolver templateResolver() {
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".html");
        //     去除缓存
        resolver.setCacheable(false);
        resolver.setTemplateMode("HTML5");
//        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    //引擎配置
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.addTemplateResolver(templateResolver());
        springTemplateEngine.addTemplateResolver(urlTemplateResolver());
        springTemplateEngine.addDialect(new TilesDialect());
        return springTemplateEngine;
    }

    @Bean
    public UrlTemplateResolver urlTemplateResolver() {
        return new UrlTemplateResolver();
    }

    @Bean
//    public ThymeleafViewResolver tilesViewResolver() {
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setViewClass(ThymeleafTilesView.class);
        resolver.setTemplateEngine(templateEngine());
        resolver.setViewNames(new String[]{"tiles/*"});
        resolver.setCharacterEncoding("UTF-8");
        resolver.setOrder(0);
        return resolver;
    }

//    /**
//     * 模板引擎解释器
//     *
//     * @return
//     */
//    @Bean
//    public ViewResolver viewResolver() {
//        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
//        viewResolver.setContentType(CONTENTTYPE);
//        viewResolver.setTemplateEngine(templateEngine());
//        viewResolver.setCharacterEncoding("UTF-8");
//        viewResolver.setOrder(1);
//        return viewResolver;
//    }
}
