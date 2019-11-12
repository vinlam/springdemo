//package com.common.component;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import ma.glasnost.orika.MapperFactory;
//import ma.glasnost.orika.converter.BidirectionalConverter;
//
//@Component
//public class OrikaMapperFactoryExtend {
//    @Autowired
//    private MapperFactory mapperFactory;
//    @PostConstruct
//    public void init() {
//        mapperFactory.getConverterFactory().registerConverter(new LocalDateTimeConverter());
//        mapperFactory.getConverterFactory().registerConverter(new LocalDateConverter());
//        mapperFactory.getConverterFactory().registerConverter(new LocalTimeConverter());
//    }
//    private class LocalDateTimeConverter extends BidirectionalConverter<LocalDateTime, LocalDateTime> {
//        @Override
//        public LocalDateTime convertTo(LocalDateTime source, Type<LocalDateTime> destinationType) {
//            return LocalDateTime.from(source);
//        }
//        @Override
//        public LocalDateTime convertFrom(LocalDateTime source, Type<LocalDateTime> destinationType) {
//            return LocalDateTime.from(source);
//        }
//    }
//    private class LocalDateConverter extends BidirectionalConverter<LocalDate, LocalDate> {
//        @Override
//        public LocalDate convertTo(LocalDate source, Type<LocalDate> destinationType) {
//            return LocalDate.from(source);
//        }
//        @Override
//        public LocalDate convertFrom(LocalDate source, Type<LocalDate> destinationType) {
//            return LocalDate.from(source);
//        }
//    }
//    private class LocalTimeConverter extends BidirectionalConverter<LocalTime, LocalTime> {
//        @Override
//        public LocalTime convertTo(LocalTime source, Type<LocalTime> destinationType) {
//            return LocalTime.from(source);
//        }
//        @Override
//        public LocalTime convertFrom(LocalTime source, Type<LocalTime> destinationType) {
//            return LocalTime.from(source);
//        }
//    }
//}
