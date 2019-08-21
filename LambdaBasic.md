# Lambda

1. 함수적 프로그래밍 패턴을 위해 Java는 8버전부터 Lambda를 지원함

2. 람다는 익명함수를 만들기 위한  expression(식)
   - 객체지향언어보다는 함수지향적 언어에서 사용됨 => 자바스크립트와 유사



### Lambda의 도입이유

1. 코드가 간결

2. Java Stream을 이용하기 위해서 람다를 이용

   - Java Stream은 collection(List, Map, Set, Array...)의 처리를 효율적으로 할 수 있음

     => 병렬처리가 가능



### 람다식의 형태

```
( 매개변수 ) -> { 실행코드 }
```

- 익명함수를 정의하는 형태로 되어 있지만 실제로는 익명클래스의 인스턴스를 생성
- 람다식이 어떤 객체를 생성하느냐는 람다식이 대입되는 interface변수가 어떤 interface인가에 달려있음
- 일반적인 interface를 정의해서 람다식으로 표현



#### interface

![1566286163805](C:\Users\student\AppData\Roaming\Typora\typora-user-images\1566286163805.png)

- 람다식으로 표현하려면 interface에 추상메소드가 1개여야함



#### main()내

![1566286242639](C:\Users\student\AppData\Roaming\Typora\typora-user-images\1566286242639.png)



### Thread

1. Thread의  subclass를 이용해서 Thread생성

   - Thread를 상속받아 Thread생성
   - 많이 안씀 => 객체지향적으로 좋은 방법이 아님

2. Runnable interface를 구현한 class를 이용해서 Thread 생성

   - run()을 overriding

3. Runnable interface를 구현한 익명 class를 이용해서 Thread생성

   - 안드로이드에서 일반적인 형태

   - 람다식으로 표현

     ![1566286558407](C:\Users\student\AppData\Roaming\Typora\typora-user-images\1566286558407.png)





### 람다식의 표현방식

```
(인자1, 인자2, 인자3...) -> {실행코드}
```

1. 매개변수의 이름은 개발자가 저장할 수 있음
2. 매개변수의 타입은 컴파일러의 타입유추에 의해서 알 수 있기 때문에 람다식에서는 일반적으로 매개변수의 타입을 지정하지 않음
3. 만약 매개변수가 1개인 경우 ( )생략가능
4. 만약 실행문이 1개의 경우 { }생략가능
5. 매개변수가 없다면 ( )생략 불가능
6. 실행문에 당연히 return 구문 존재 가능
7. 실행문에 return 구문 1개만 존재하면 return키워드 생략 가능



### 람다식 설명

1. 람다식은 interface type의 instance를 생성하는 expression(식)
2. 람다식은 결국 익명 객체를 생성하는 코드
3. 람다식이 생성하는 객체는 결국 어떤 interface type의 변수에 assign되는가에 달려있음
4. 이렇게 람다식으로 만들어지는 객체의 interface type을 람다의 타겟타입이라고 함



### 람다의 target type

람다식으로 만들어지는 객체의 interface type

- target  type은 아무 interface나 사용할 수 없음
- 람다의 target type이 되려면 해당 interface는 반드시 추상 메소드 1개만 있어야함
- 그래서 interface를 사용할 때 어노테이션을 이용해서 check를 할 수 있음
- @FunctionalInterface를 이용해서 해당 interface가 람다의 target type이 될 수 있는지 compiler에 의한 check를 할 수 있음(함수의 interface) 



### Runnable interface

- Thread를 생성할 때 Runnable interface를 사용할 수 있음
- 이 Runnable interface는 public void run()이라는 추상 메소드 1개만 가지고 있음
- Runnable interface는 함수적 인터페이스라고 할 수 있음
- 이벤트를 처리하는 interface는 대체로 함수적 interface





### 람다식 정의해서 사용시 주의해야 할 점

- 클래스의 멤버 : 필드 , 메소드
- this keyword사용시 주의
- this : 현재 사용되는 객체의 reference
- 람다식은 익명 객체를 만들어 내는 코드
- 람다식의 실행코드내에서 this keyword를 쓰면 익명객체를 지칭하지 않음
- 람다식에서는 지역변수를 readonly 형태로 사용
- 지역변수는 stack에 저장되고 method가 호출되면 생기고 method가 끝나면 사라짐



### Outer 클래스안에 Inner 클래스가 있는 경우

- Inner 클래스에서 Outer클래스에 있는 변수에 접근시

![1566290542318](C:\Users\student\AppData\Roaming\Typora\typora-user-images\1566290542318.png)

​		- OuterClass.this 형태로 접근

![1566290571029](C:\Users\student\AppData\Roaming\Typora\typora-user-images\1566290571029.png)

- 람다식을 사용하려면 InnerClass의 instance가 존재해야함

  - inner class의 instance를 생성하려면  outer class의 instance부터 생성해야함

    ![1566290737559](C:\Users\student\AppData\Roaming\Typora\typora-user-images\1566290737559.png)