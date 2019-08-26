1. Multi Processing

   cpu안에 Core가 2개 이상일 경우에만 의미를 가지는 용어

   (시간적으로 동시간에 다른 일을 같이 처리할 수 있는 경우)

   

2. Multi Tasking

   Core의 개수와는 상관이 없음

   여러개의 프로그램을 시분할방식을 이용해서

   하나의 Core가 마치 시간적으로 동시에 수행되는 것처럼 보이게 하는것



3. Multi Threading

   Core의 개수와는 상관 없어요

   하나의 프로그램은 내부적으로 여러개의 작업(thread)이 동시에 진행되어야 할 경우가 있음

   하나의 프로그램 내에서 여러개의 작업이 동시에 실행되는것처럼 보여야 할때 사용

    Core를 시분할을 통해서 각 작업에 할당



### Process

- OS에서 실행중인 하나의 Application 

- OS로부터 실행에 필요한 메모리를 할당 받아서 Application코드를 실행





## JAVA THREAD

1. Concurrency(병행성) vs Parallelism(병렬성)

2. Process의 종료는 process안에서 파생된 모든 Thread가 종료되는 순간 발생
3. main thread가 모든 작업을 처리하는 것보다 다수의 thread로 작업을 나누어 처리하는 방식이 효율적
4. MultiThread 



- Thread Sleep

  : CPU 점유를 중지하고 특정시간 동안 아무것도 안하고 Thread가 자고 있도록 하는 method

  - 실제로는 running중이던 thread에서 일을 하는 core가 분리되고 blocked상태로 들어감
  - sleep이 끝나면 runnable상태로 들어감
  - 분리된 core는 scheduler에 의해 선택된 다른 thread를 선택한다







## Synchronization

하나의 자원을 여러 Thread가 사용하려 할때, 한 시점에 하나의 Thread만이 사용할 수 있도록 하는 것을 의미

ex ) 영화관 좌석 선택

- 모든 공유객체는 Monitor객체를 하나만 가지고 있음
- 일단 Monitor를 한 Thread가 가지고 있으면 다른 Thread는 해당 Instance의 동기화 method(동기화 block)를 실행시킬 수 없다
- java의 synchronized keyword를 사용해 Monitor객체를 획득