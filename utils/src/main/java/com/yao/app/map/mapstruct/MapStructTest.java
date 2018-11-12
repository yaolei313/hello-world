package com.yao.app.map.mapstruct;


import com.yao.app.json.JsonMapper;

/**
 * Created by yaolei02 on 2018/8/31.
 */
public class MapStructTest {
    public static void main(String[] args) {
        Car car = new Car("Morris", null, CarType.PORSCHE);

        //when
        CarDto carDto = CarMapper.INSTANCE.carToCarDto(car);

        System.out.println(JsonMapper.NON_NULL_MAPPER.toJson(carDto));
        //then
        //Assertions.( carDto != null ,"" );
        //Assert.isTrue( carDto.getMake() ).isEqualTo( "Morris" );
        //Assert( carDto.getSeatCount() ).isEqualTo( 5 );
        //Assert( carDto.getType() ).isEqualTo( "SEDAN" );
    }
}
