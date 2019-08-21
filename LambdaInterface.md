# Lambda

- 람다식은 추상메소드가 1개의 인터페이스의 객체를 생성하는 표현식

- 이 때 사용하는 인터페이스(함수적 인터페이스)를 직접 우리가 만들어서 사용?

  => 그렇지 않다. 람다식이 대입되는 target type은 일반적으로 Java가 제공하는 API이용

  ==> ex ) Runnable, Event처리 interface를 target type으로 사용

- Java에서는 람다의 target type으로 사용될 수 있는 interface를 여러개 만들어서 우리에게 package형태로 제공( java.util.function package )

- 함수적 인터페이스

  - 람다식이 대입될 수 있는 target type으로 사용할 수 있는 interface를 지칭
  - 추상 메소드 1개

- 제공되는 interface는 총 5가지 종류로 분류가능

  1. Consumer
  2. Supplier
  3. Function
  4. Operation
  5. Predicate



## 1급 함수

- 일반적인 method호출은 사용하는 data가 인자로 전달되는 형태
- 람다식을 이용하면 method를 호출할 때 data가 아니라 실행 코드를 넘겨줄 수 있음(눈에 보이는 형태만)

- 일반적으로 프로그래밍 언어에서 이렇게 함수를 다른 함수의 인자로 사용할 수 있는데 이런 함수를 first-classes function이라고 함(1급 함수) : JavaScript가 대표적
- Java언어도 람다를 도입해서 마치 1급함수를 사용하는 것처럼 쓸수 있음



## Consumer

- 매개변수 있고 리턴값이 없음

- accept()라는 method를 제공

- 값을 소비만하는 역할을 담당

  - accept()라는 함수의 리턴 타입은 void

- Consumer

  ```java
  Consumer<String> consumer = t->{
      System.out.println(t); //인자로 무엇을 할것인지
  };
  consumer.accept("이수연");
  
  => 이수연
  
  - 추상메소드가 1개이므로 인자가 accept의 인자라고 자동으로 처리함
  ```

- BiConsumer : 인자 2개

  ```java
  BiConsumer<String, String> biConsumer = (a,b)->{
  	System.out.println(a+b);
  };
  biConsumer.accept("이수연","짱짱맨");
  
  => 이수연짱짱맨
  ```

- IntConsumer : 인자가 정수

  ```java
  IntConsumer intConsumer = i->System.out.println(i);
  intConsumer.accept(100);
  
  => 100
  ```

  

- ObjIntConsumer : 인자 1. String   2.Int 순서대로

  ```JAVA
  ObjIntConsumer<String> objIntConsumer = (a,b)->{
      System.out.println(a+b);
  };
  objIntConsumer.accept("Hello",100);
  
  => Hello100
  ```

  



## Supplier

- 매개변수가 없고 리턴값이 존재

- getXXX( )라는 method 존재

- Supplier

  - Supplier를 이용해서 랜덤으로 1명의 친구를 출력

  ```java
  public static void main(String[] args) {
      final List<String> myBuddy = Arrays.asList("홍길동", "김길동", "이순신", "강감찬");
  
      // Supplier를 이용해서 랜덤으로 1명의 친구를 출력해 보아요
      Supplier<String> supplier = () -> {
          return myBuddy.get((int) (Math.random() * 4)); 
          // get : List에서 특정 index의 값을 뽑는 method
      };
  
      System.out.println(supplier.get());
  }
  ```

  

- IntSupplier : 정수값을 1개 리턴하는 supplier

  - 로또 번호를 자동으로 생성하고 출력하는 간단한 method 작성
  - generateLotto(supplier, consumer)

  ```java
  public class Exam05_LambdaUsingSupplier {
      // 로또 번호 (1~46) 를 자동으로 생성하고 출력하는 간단한 Method를 작성
      public static void generateLotto(IntSupplier supplier,Consumer<Integer> consumer){
          // 처리하는 코드 자체가 넘어오는 형태
          Set<Integer> lotto = new HashSet<Integer>();
          while (lotto.size() != 6) {
              lotto.add(supplier.getAsInt());
          }
  
          for (Integer i : lotto) {
              consumer.accept(i);
          }
      }
  
  	public static void main(String[] args) {
          // 처리하는 코드 자체가 넘어가는 형태
  		generateLotto(() -> {
  			return ((int) (Math.random() * 45)) + 1;
  		}, t -> {
  			System.out.println(t + " ");
  		});
  	}
  }
  
  =>  17 
  	35 
  	36 
  	22 
  	42 
  	12 
  ```

  



## Function

- 입력매개변수와 리턴값이 존재

- applyXXX() method 제공

- 일반적으로 입력매개변수를 리턴값으로 mapping시킬 때 사용

- ```
  Function<T,R> func = t->{return ~~~};
  T : 입력 매개변수의 generic
  R : 리턴값의 generic
  ```

- Function은 입력은 객체로 들어오는데 return은 다른형태로 나갈 때 많이 사용

  ```java
  // Student VO class를 정의해요
  class Exam06_Student{
  	private String sName;
  	private int sKor;
  	private int sEng;
  	private int sMath;
  	
  	public Exam06_Student() {
  		super();
  	}
  
  	public Exam06_Student(String sName, int sKor, int sEng, int sMath) {
  		super();
  		this.sName = sName;
  		this.sKor = sKor;
  		this.sEng = sEng;
  		this.sMath = sMath;
  	}
  
  	public String getsName() {
  		return sName;
  	}
  	public void setsName(String sName) {
  		this.sName = sName;
  	}
  	public int getsKor() {
  		return sKor;
  	}
  	public void setsKor(int sKor) {
  		this.sKor = sKor;
  	}
  	public int getsEng() {
  		return sEng;
  	}
  	public void setsEng(int sEng) {
  		this.sEng = sEng;
  	}
  	public int getsMath() {
  		return sMath;
  	}
  	public void setsMath(int sMath) {
  		this.sMath = sMath;
  	}
  }
  
  public class Exam06_LambdaUsingFunction {
  	
  	//static 사용 이유, 객체 안만들고 main에서 바로 access해서 사용하려고
  	private static List<Exam06_Student> students = 
  			Arrays.asList(new Exam06_Student("홍길동",10,20,30),
  					new Exam06_Student("김길동", 50, 60, 70),
  					new Exam06_Student("이순신", 90, 20, 30),
  					new Exam06_Student("이수연", 10, 100, 70));
  	
  	private static void printName(Function<Exam06_Student, String> function) {
  		// 처리하는 코드 자체가 넘어가는 형태
  		for(Exam06_Student s : students) {
  			System.out.println(function.apply(s));
  		}
  	}
  	
  	
  	private static double getAvg(ToIntFunction<Exam06_Student> function) {
  		// ToIntFunction -> return값이 int
  		 int sum=0; 
  		 for(Exam06_Student s : students) {
  			 sum += function.applyAsInt(s); 
  		 } 
  		 return (double)sum / students.size();
  		
  	}
  	
  	
  	
  	public static void main(String[] args) {
  		//학생이름을 출력!!
  		printName(t -> {return t.getsName();});
  		
  		// getAvg()라는 static method를 만들어서 다음의 내용을 출력하세요
  		// 학생들의 국어성적 평균 => getAvg()
  		System.out.println("국어평균 : "+getAvg(t -> t.getsKor()));
  		// 학생들의 영어성적 평균 => getAvg()
  		System.out.println("영어평균 : "+getAvg(t -> t.getsEng()));
  		// 학생들의 수학성적 평균 => getAvg()
  		System.out.println("수학평균 : "+getAvg(t -> t.getsMath()));
  	}
  }
  ```



## Operator

- Function과 하는일이 거의 비슷

- applyXXX() method 제공

- method는 매개값을 이용해 연산 수행 후 동일한 타입으로 리턴

- IntBinaryOperator

  - 최대값, 최소값을 구해보자

  ```java
  public class Exam07_LambdaUsingOperator {
  
  	private static int arr[] = {100,92,50,89,34,27,99,3};
  	//getMaxMin()을 static method로 만들꺼에요
  	// IntBinaryOperator => interface 타입
  	private static int getMaxMin(IntBinaryOperator operator) {
  		int result = arr[0];
  		for(int k : arr) {
  			result = operator.applyAsInt(result, k);
  		}
  		return result;
  	}
  	
  	public static void main(String[] args) {
  		System.out.println("최대값 : "+ getMaxMin((a,b) -> {
  			//최대값
  			return a>=b ? a : b;
  		}));
  		System.out.println("최소값 : "+ getMaxMin((a,b) -> {
  			//최소값
  			return a<b ? a : b;
  		}));
  	}
  
  }
  
  ```

  



## Predicate

- 입력매개변수 있고 boolean 리턴

- testXXX( ) method 제공

- 입력매개변수값을 조사하여 TRUE, FALSE값을 리턴해야 하는 경우 사용

- Predicate

  ```java
  // Student VO class를 정의해요
  class Exam08_Student{
  	private String name;
  	private int kor;
  	private int eng;
  	private int math;
  	private String gender;
  	
  	public Exam08_Student() {
  		super();
  	}
  
  	public Exam08_Student(String name, int kor, int eng, int math, String gender) {
  		super();
  		this.name = name;
  		this.kor = kor;
  		this.eng = eng;
  		this.math = math;
  		this.gender = gender;
  	}
  
  	public String getName() {
  		return name;
  	}
  	public void setName(String name) {
  		this.name = name;
  	}
  	public int getKor() {
  		return kor;
  	}
  	public void setKor(int kor) {
  		this.kor = kor;
  	}
  	public int getEng() {
  		return eng;
  	}
  	public void setEng(int eng) {
  		this.eng = eng;
  	}
  	public int getMath() {
  		return math;
  	}
  	public void setMath(int math) {
  		this.math = math;
  	}
  	public String getGender() {
  		return gender;
  	}
  	public void setGender(String gender) {
  		this.gender = gender;
  	}
  	
  }
  
  public class Exam08_LambdaUsingPredicate {
  	private static List<Exam08_Student> students =
  			Arrays.asList(
  					new Exam08_Student("홍길동",10,20,30,"남자"),
  					new Exam08_Student("박길동",10,20,30,"남자"),
  					new Exam08_Student("신사임당",10,20,30,"여자"),
  					new Exam08_Student("유관순",10,20,30,"여자"),
  					new Exam08_Student("이순신",10,20,30,"남자"));
  	
  	// static method를 하나 정의하는데 성별에 따른 특정 과목의 평균을 구하는
  	// 작업을 할 거에요
  	private static double avg(Predicate<Exam08_Student> predicate, 
  			ToIntFunction<Exam08_Student> function) {
  		int sum = 0;
  		int count = 0;
  		
  		for(Exam08_Student s : students) {
  			if(predicate.test(s)) {
  				count++;
  				sum += function.applyAsInt(s);
  			}
  		}
  		
  		return (double)sum/count;
  	}
  	
  	public static void main(String[] args) {
  		System.out.println("남자 수학 평균"+
                             avg(t -> t.getGender().equals("남자"), t -> t.getMath()));
  		System.out.println("여자 영어 평균"+
                             avg(t -> t.getGender().equals("여자"), t -> t.getEng()));
  		}
  }
  
  =>  남자평균30.0
  	여자평균20.0
  
  ```

  