1. Java Lambda
   - 람다 기본 
     - 람다식의 의미
     - 람다식의 장점
       1. 코드의 간결화
       2. Stream의 사용
   - 람다 표현방법 : case by case로 설명
   - 람다 scope 
     - 필드를 사용하는 법과 지역변수를 사용하는 방법
     - 람다내에서 this는 다른객체를 지칭
   
   - Java8 이상에서 지원
   
   - 람다식은 익명함수를 만드는 표현식(이름이 없는 함수)
   
     - 자바는 함수라는 개념이 존재하지 않음
     - 객체안에 method개념으로 기존에 다른 언어에서의 함수를 사용을 함
   
   - 자바에서 람다를 도입한 이유
   
     - 코드의 간결성
     - Stream과의 연동
   
   - 람다의 표현식 => (매개변수1, 매개변수2.....) -> { }
   
     - 실제로는 자바의 익명 class의 instance를 생성
   
   - 람다의 타겟타입 => 람다식이 대입되는 interface를 지칭
   
   - 람다의 타겟타입이 되려면 인터페이스여야해요
   
     해당 인터페이스안에 추상 메소드가 반드시 1개만 존재
   
     컴파일 단계에서 특정 interface가 람다의 타겟타입인지를 확인하는 annotation이 있음
   
     - @FunctionalInterface
   
   - 람다식안에서 변수사용(member 사용하는 방식과 local variable) this keyword의 의미가 다름
   
   - 결국 람다식은 특정 interface type의 변수에 저장이 되는 형태로 사용됨
   
     - ex ) Runnable a = ( ) -> { }
     - Runnable은 함수적 인터페이스 형태의 interface
     - Runnable이 람다식의 target type
   
   - 이런 특정 interface는 우리가 만들어 사용하는가?
   
     - 우리가 interface를 정의해서 사용할 수 도 있음, 하지만 일반적인 형태는 아님
   
     - 자바에서는 java.util.function이라는 package를 제공
   
       이 안에 우리가 람다식의 traget type으로 활용이 가능한 interface를 넣어서 제공
   
   - 이런 우리에게 제공되는 함수적 interface는 총 5가지
   
     - Consumer : 인자를 사용하고 리턴이 없는 추상메소드를 가지고 있음
     - Supplier : 사용되는 인자가 없음, 리턴값이 있음
     - Function :
     - Operator
     - Predicate
2. Java Stream
3. Java Thread
4. Java IO
5. Java NIO
6. Java