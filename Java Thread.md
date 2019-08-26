# Java Thread

Java Application은 main thread가 main( ) method를 호출해서 실행

프로그램은 main method( )가 종료될 때 종료되는게 아니라 프로그램내에서 파생된 모든 Thread가 종료될 때 종료됨



### Thread 주기

1. Thread의 생성

   => Thread class를 상속받아서 class를 정의하고 객체 생성해서 사용

   => Runnable interface를 구현한 class를 정의하고 객체를 생성해서 Thread생성자의 인자로 넣어서 Thread생성

2. start( ) 

   : thread를 실행시키는게 아니라 runnable상태로 전환

3. JVM안에 있는 Thread scheduler에 의해 하나의 Thread가 선택되서 thread가 running 상태로 전환

4. 어느시점이 되면 Thread scheduler에 의해서 runnable상태로 전환(언제 전환되는지는 모름)

5. runnable상태에 있는 Thread 중 하나를 선택해서  running상태로 전환



### JavaFX

자바를 위한 GUI 표준 라이브러리

- Application interface를 상속받음

- 화면을 띄우는 thread는 JavaFX Application Thread

- JavaFX는 내부적으로 화면을 제어하는 Thread를 생성해서 사용

- 이 thread가 어느 시점에 start라는 method를 호출

- BorderPane : Layout manager

- TilePane : 격자 형태

  - .setPrefColumns( ) : 열의 개수
  - .setPrefRows( ) : 행의 개수

- TextArea

- setOnAction( )

  ```java
  btn.setOnAction(t->{
  	new Thread(()->{ //람다식은 runnable interface가 가지고 있는 run이라는 실행 method지칭
          // 화면제어는 JavaFX Application Thread가 담당
          // Platform.runLater() 
          // : 나중에 화면에 thread를 실행시켜달라고 JavaFX Application Thread에 요청 
  		Platform.runLater(()->{
  		textarea.appendText("이수연");
  		});	
  	})
  }).start(); // 이미 runnable상태이기 때문에 start()로 시작
  ```

- Scene객체

  ```java
  @Override
  public void start(Stage primaryStage) throws Exception {}
  // primaryStage는 실제 화면에 띄워지는 객체
  
  // root는 BorderPane(Layout Manager)객체
  Scene scene = new Scene(root);
  primaryStage.setScene(scene);
  primaryStage.setTitle("Thread 예제입니다!");
  primaryStage.show();
  ```

- Thread.currentThread().getName() 

  : 현재 실행되는 method를 호출한 Thread의 이름

- launch( ) 

  : main method내에서 launch( ) 호출되면 start 호출됨

- ProgressBar : 막대형태

- ProgressIndicator : 원형태



### Java

- IntStream.rangeClose(1,5) : 1,2,3,4,5에 대한 IntStream생성

- ```java
  btn.setOnAction(t->{
      //버튼에서 action이 발생(클릭)했을 때 호출
      //1부터 5까지 5번 반복 => for문 이용, stream이용시
      IntStream intStream = IntStream.rangeClosed(1, 5);
      intStream.forEach(value->{	//Consumer 사용
          //1부터 5까지 5번 반복하면서 Thread를 생성
          Thread thread = new Thread(()->{//runnable은 함수적 인터페이스로 
              //run이라는 추상메소드를 람다식으로 표현할 수 있음
              for(int i=0; i<5; i++) {
                  try {
                      Thread.sleep(3000);
                      printMsg(i+" : "
                               +Thread.currentThread().getName());
                  } catch (InterruptedException e) {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
                  }
              }
          });
  
          thread.setName("ThreadNumber-"+value);
          thread.start(); //runnable상태로 들어감
  });
      
      
  => 0 : ThreadNumber-4
  0 : ThreadNumber-1
  0 : ThreadNumber-2
  0 : ThreadNumber-5
  0 : ThreadNumber-3
  1 : ThreadNumber-3
  ...
  3 : ThreadNumber-3
  4 : ThreadNumber-3
  4 : ThreadNumber-2
  4 : ThreadNumber-5
  4 : ThreadNumber-1
  4 : ThreadNumber-4
  
  ```

- Thread의 인자는 Runnable객체

- Synchronized

  공유 객체 접근시 1개의 Thread만 접근 가능

  1. method 동기화

     ```java
     public synchronized void assignNumber(int number){ }
     ```

  2. method내, 필요한 부분만 동기화 block : 추천

     ```java
     		synchronized (this) {
     			this.number = number;
     			try {
     				Thread.sleep(3000);
     				System.out.println("현재 공용객체의 number : "
     						+ this.number);
     			}...
     		}
     	}
     ```

- sleep( ) : sleep하면 해당 thread는 잠시 block상태로 빠지고 thread scheduler는 다른 thread를 running시킨다. 그리고 sleep( )이 끝나면 runnable상태로 전환

- notify( ) : 현재 wait( ) 상태에 있는 Thread를 깨워서 runnable상태로 전환

- wait( ) :  자기가 가지고 있는 monitor객체를 놓고 스스로 wait block에 들어감

- setPriority( ) : 인자의 숫자가 클수록 우선순위 높음

- interrupt( ) : method가 실행된다고 바로 thread가 종료되는것은 아니다.

  ​					  interrupt( ) method가 호출된 Thread는 sleep( ) 과 같이 block상태에 들어가야 interrupt를					 시킬 수 있음

- thread.setDaemon(true) : 해당 Thread를 daemon Thread로 설정

  - 자식 Thread가 된다고 생각
  - 부모 Thread가 중지되면 자동적으로 자식 Thread도 중지
  - X눌러서 화면끄면 부모 Thread가 죽기 때문에 자동으로 자식 Thread가 죽어서 프로그램이 종료
  - Thread의 상태를 지정해주는 것이기 때문에 start( ) method가 호출되기 전에 지정되어야한다





## Thread Pool

Thread Pool에 Thread를 미리 만들어놓고 가져다 쓰는 방식

- thread생성할 때마다 new로 생성하면 overhead가 크다

- 병행(병렬) 처리가 많아지면 많아질수록 Thread개수가 증가하게 돼고 이에 따른 Thread생성과 Scheduling에 대한 overhead가 생김 

  => 성능저하의 주요원인

- Java7이상부터 패키지로 제공

- newCachedThreadPool( )

  - 처음에 만들어지는 Thread Pool안에 Thread가 없음
  - 만약 필요하면 내부적으로  Thread 생성
  - 만드는 Thread의 수는 제한이 없음
  - 60초 동안 Thread가 사용되지 않으면 자동적으로 Thread삭제

  ```java
  ExecutorService executorService = Executors.newCachedThreadPool();
  ```

- newFixedThreadPool( )

  - 처음에 만들어지는 Thread Pool안에 Thread가 없음
  - 만약 필요하면 내부적으로  Thread 생성
  - 인자로 들어온 int수만큼의 Thread만 만들 수 있음
  -  Thread가 사용되지 않더라도 만들어진 Thread는 계속 유지

- Thread Pool에 있는 Thread의 개수

  ```java
  int threadNum = ((ThreadPoolExecutor)executorService).getPoolSize();
  ```

- Thread의 실행

  - 인자로 Runnable 객체
  - 내부적으로 Thread만들고 실행
  - executorService가 이 모든것을 관리

  ```java
  executorService.execute(runnable); 
  ```

- 종료

  ```java
  executorService.shutdown();
  ```

  



### Callable

return이 있는 Runnable

=> return값이 있는 Thread를 실행할 때 사용

- call( ) 오버라이딩

- Runnable 

  ```java
  Runnable runnable = () -> { }
  executorService.execute(runnable);
  ```

- Callable

  ```java
  Callable<String> callable = new Callable<String>(){ }
  Future<String> future = executorService.submit(callable);
  ```

- Future : pending객체 

  - 실제로 객체가 들어가 있지는 않고 담을 수 있는 형태만 마련한 상태
  - Future객체의 특정 method를 호출할 때 사용된다

  ```java
  Future<String> future = excutorService.submit(callable);
  ```

- get( ) 

  - blocking method
  - 나머지가 실행이 안되기 때문에 순차처리가 됨

  ```java
  String result = future.get();
  ```



### ExecutorCompletionService

기존 Thread Pool을 가지고 좀 더 성능이 향상된 Thread Pool 만들기

```java
// Thread Pool
ExecutorService executorService = Executors.newCachedThreadPool();

// 확장된 기능이 있는 Thread Pool
ExecutorCompletionService<Integer> executorCompletionService =
    					new ExecutorCompletionService<Integer>(executorService); 
```