# Java Stream

- Stream은 Java 8에서 도입됨

- java.io안에 있는 Stream과는 다름

- 사용 용도 

  - 컬렉션처리(List, Set, Map, 배열)를 위해서 사용됨

  - 컬렉션안의 데이터를 반복시키는 반복자의 역할을 하는게 stream

  - ex) ArrayList안에 학생 객체가 5개 있으면 그 5개를 하나씩 가져오는 역할을 수행

    ​	=> 이렇게 가져온 데이터를 람다식을 이용해서 처리가능



### Stream

- 사람이름 출력 

  1. 일반 for문
  2. Iterator
  3. Stream

  ```java
  private static List<String> myBuddy = 
  			Arrays.asList("홍길동","김길동","최길동","박길동");
  
  // 1. 일반 for문
  //    - 첨자와 반복자 사용 => 비효율적
  for(int i=0; i<myBuddy.size(); i++){
      System.out.println(myBuddy.get(i));
  }
  
  // 2. Iterator
  //    - 첨자가 없음
  Iterator<String> iter = myBuddy.iterator();
  while(iter.hasNext()){
      System.out.println(iter.next());
  }
  
  // 3. Stream
  //	  - 반복자가 필요없음. 내부 반복자 이용
  //	  - 병렬처리 가능, 로직에만 집중
  //	  - 좀 더 객체지향적
  Consumer<String> consumer = t->{
      System.out.println(t+", "+Thread.currentThread().getName());
  }
  
  //stream은 컬렉션 안의 데이터를 반복시킴
  // stream() : 하나의 thread로 처리
  Stream<String> stream1 = myBuddy.stream();
  stream1.forEach(consumer);
  
  Stream<String> stream2 = myBuddy.parallelStream();
  stream2.forEach(consumer);  
  
  // parallelStream() : 병렬처리 stream
  //					  필요한만큼 thread만들어서 병렬처리한다
  Stream<String> stream = myBuddy.parallelStream();
  stream.forEach(consumer);
  
  3. => 홍길동, main
      김길동, main
      최길동, main
      박길동, main
      
      최길동, main
      박길동, main
      김길동, ForkJoinPool.commonPool-worker-2
      홍길동, ForkJoinPool.commonPool-worker-1
  ```

  

- Stream을 이용한 성적평균 계산

  ```java
  // Exam01_Student VO 있음
  private static List<Exam01_Student> students = 
      Arrays.asList(
      new Exam01_Student("홍길동",10,20),
      new Exam01_Student("박길동",10,20),
      new Exam01_Student("신사임당",10,20),
      new Exam01_Student("유관순",10,20),
      new Exam01_Student("이순신",10,20));
  	
  
  public static void main(String[] args) {
      // arrayList는 stream이라는 메소드가 잇음
      Stream<Exam01_Student> studentStream = students.stream();
      // function lambda식을 이용하여 객체를  mapping
      // t라는 객체가 들어가서 getkor()를 이용
      double avg = 
          studentStream.mapToInt(t->t.getKor()).average().getAsDouble();
      System.out.println("국어성적의 평균 : "+avg);
  }
  
  => 국어성적의 평균 : 10.0
  ```

  

### List로부터 Stream 생성

- List<String> 로부터 stream 생성

  ```java
  private static List<String> names = 
      Arrays.asList("홍길동","김길동","최길동");
  
  Stream<String> stream1 = names.stream();
  
  // 람다식형태로 consumer가 들어간 형태
  stream1.forEach(t->System.out.println(t));
  
  => 홍길동
  	김길동
  	최길동
  ```

- int형 배열로부터 stream생성

  ```java
  private static int myArr[] = {10,20,30,40,50};
  
  IntStream stream2 = Arrays.stream(myArr);
  System.out.println(stream2.sum());
  
  => 150
  ```

- IntStream의 rangeClosed method

  : 지정한 범위만큼 연산

  ```java
  IntStream stream3 = IntStream.rangeClosed(1,10);
  System.out.println(stream3.sum());
  
  => 55
  ```

- File로부터 stream 생성

  ```java
  //File객체(java.io)와 유사한 java.nio에 포함된 class가 바로 Path
  Path path = Paths.get("assets/readme.txt");
  
  try{
      Stream<String> stream4 = Files.lines(path, Charset.forName("UTF-8"));
      stream4.forEach(t->System.out.println(t));
      stream4.close();
  }catch()...
      
  => readme.txt에 적혀있는 내용 출력
  ```



## Reduction

- 대량의 데이터를 가공해서 축소하는 개념
  - ex ) sum, average, count, max, min
- 만약 Collection안에 reduction하기가 쉽지 않은 형태로 데이터가 들어있으면 중간처리과정을 거쳐서 reduction하기 좋은 형태로 변환
- Stream은 pipeline을 지원(stream을 연결해서 사용할 수 있음)
- 한번 stream처리를 하면 stream이 닫힌다
- stream을 sql문처럼 사용

- ex ) 직원객체를 생성해서 ArrayList안에 여러명의 직원을 저장

  ​	   이 직원중에 IT에 종사하고 남자인 직원을 추려서 해당 직원들의 연봉 평균을 구해라

  - Exam03_Employee VO

    - Comparable을 구현해 객체들끼리 정렬가능하고 비교가능한 형태로 만듦
    - compareTo( ) :
      1. Comparable을 구현하면 반드시 오버라이딩해야하는 method
      2. 정수값을 리턴
      3. 양수가 리턴되면 순서를 바꿈
      4. 0이나 음수가 리턴되면 순서변화 없음
    - equals( ) 오버라이딩 : 
      1. 오버라이딩 안하면 메모리 주소로 비교
      2. 특정 조건을 만족하면 객체가 같다고 오버라이딩

    ```java
    class Exam03_Employee implements Comparable<Exam03_Employee>{ 
        //Comparable을 구현해야 객체들끼리 정렬가능하다, 객체들끼리 비교가능한 형태로 만들자
    	private String name;
    	private int age;
    	private String dept;
    	private String gender;
    	private int salary;
    	
    	// 생성자
    	public Exam03_Employee() {
    		super();
    	}
    	public Exam03_Employee(String name, int age, String dept, String gender, int salary) {
    		super();
    		this.name = name;
    		this.age = age;
    		this.dept = dept;
    		this.gender = gender;
    		this.salary = salary;
    	}
    
    	// getter & setter
    	public String getName() {
    		return name;
    	}
    	public void setName(String name) {
    		this.name = name;
    	}
    	...
    	
        @Override
    	public int hashCode() {
    		final int prime = 31;
    		int result = 1;
    		result = prime * result + age;
    		result = prime * result + ((dept == null) ? 0 : dept.hashCode());
    		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
    		result = prime * result + ((name == null) ? 0 : name.hashCode());
    		result = prime * result + salary;
    		return result;
    	}    
            
        //distinct()사용시 내부적으로 equals이용
    	@Override
    	public boolean equals(Object obj) {
    		// 만약 overriding을 하지 않으면 메모리 주소가지고 비교
    		// 내가 원하는 방식으로 overriding을 해서 특정 조건을 만족하면
    		// 객체가 같아!! 라는 식으로 작성해보자
    		boolean result = false;
    		Exam03_Employee target = (Exam03_Employee)obj;
    		if(this.getName().equals(target.getName())) {
    			result = true;
    		}else {
    			result = false;
    		}
    		return result;
    	}
    
    	@Override
    	public int compareTo(Exam03_Employee o) { 
            //Comparable interface 구현할 때 반드시 overriding해야함
    		// 정수값을 리턴
    		// 양수가 리턴되면 순서를 바꿈
    		// 0이나 음수가 리턴되면 순서를 바꾸지 않음
    		int result = 0;
    		if(this.getSalary() >= o.getSalary()) {
    			result = 1;
    		}else if(this.getSalary() == o.getSalary()) {
    			result = 0; //위치변화 안주겟다
    		}else {
    			result = -1;
    		}
    		
    		return result;
    	}
    	
    }
    ```

  - List<Exam03_Employee>

    ```java
    private static List<Exam03_Employee> employees =
        Arrays.asList(
        new Exam03_Employee("홍길동",20,"IT","남자",2000),
        new Exam03_Employee("최길동",30,"Sales","여자",3000),
        new Exam03_Employee("고길동",40,"IT","남자",4000),
        new Exam03_Employee("윤길동",28,"Sales","남자",5000),
        new Exam03_Employee("이수연",25,"IT","여자",6000),
        new Exam03_Employee("뇸뇸",35,"IT","여자",3500),
        new Exam03_Employee("꾸꾸",50,"IT","남자",2500),
        new Exam03_Employee("뿌뿌",40,"Sales","남자",2800),
        new Exam03_Employee("홍길동",20,"IT","남자",2000));
    ```

    

  - main

    ```java
    Stream<Examp+Employee> stream =
        employees.stream();
    
    // stream의 중간처리와 최종처리를 이용해서 원하는 작업을 하자
    // filter method는 결과값을 가지고 있는 stream을 리턴해줌(it부서인 객체가 들어있댜)
    double avg = stream.filter(t->t.getDept().equals("IT"))		//중간처리
        				.filter(t->t.getGender().equals("남자"))	//중간처리
        				.mapToInt(t->t.getSalary()) 	
        								//stream에 해당하는 정보를 모두 int로 변경
        				.average().getAsDouble();
    									//average는 최종처리
    
    //lazy처리 :스트림 연산은 가능하면 지연(lazy)처리 됨
    //			지연처리란 결과가 필요하기 전에는 실행되지 않음을 의미(성능 최적화)
    
    // stream은 reduction하는 최종처리가 있는지 확인하고 없으면 중간처리를 하지않음
    System.out.println("IT부서의 남자직원 연봉 평균 : "+avg);
    
    => IT부서의 남자직원 연봉 평균 : 2625.0
    ```

  - filter : stream이 제공하는 method

  - 나이가 35이상인 직원 중 남자 직원의 이름 출력

    ```java
    stream.filter(t->t.getAge() >= 35)
        						.filter(t->t.getGender().equals("남자"))
        						.forEach(System.out.println(t->t.getName()));
    
    => 고길동
    	꾸꾸
    	뿌뿌
    ```

  - distinct : 중복제거를 위한 method

    ```java
    int temp[] = {10,20,30,40,50};
    IntStream s = Arrays.stream(temp);
    s.distinct().forEach(t->System.out.println(t));
    
    => 10
    	20
    	30
    	50
    ```

  - 객체에 대한 중복제거

    ```java
    employees.stream()
        		.distinct()
        		.forEach(t->System.out.println(t.getName()));
    
    => 홍길동 최길동 고길동 윤길동 이수연 뇸뇸 꾸꾸 뿌뿌
    // 마지막 중복된 홍길동이 빠짐
    ```

  - sorted( ) : 정렬

    - 기본적으로는 오름차순 정렬

  - 부서가 IT인 사람을 연봉순으로 출력

    ```java
    employees.stream()
        		.distinct()
        		.filter(t->t.getDept().equals("IT"))
        		.sorted(Comparator.reverseOrder())	//내림차순
        		.forEach(t->System.out.println(t.getName() + ", "+t.getSalary()));
    
    => 이수연, 6000
        고길동, 4000
        뇸뇸, 3500
        꾸꾸, 2500
        홍길동, 2000
    ```

  - 반복함수

    1. forEach( ) : 최종 처리 함수, 중간 처리 함수로는 사용 불가
    2. peek( ) : 중간 처리 함수

    ```java
    employees.stream()
        		.peek(t->System.out.println(t.getName()))
        		.mapToInt(t->t.getSalary)
        		.forEach(t->System.out.println(t));
    
    => 홍길동 2000
    	최길동 3000	
    	고길동 4000	
    	윤길동 5000
    	이수연 6000
    	뇸뇸 3500
        꾸꾸 2500
        뿌뿌 2800
        홍길동 2000
    ```

  - allMatch( ) : 확인용 최종 처리 함수

  - 50살이상인 사람만 추출해서 이름 출력

    ```java
    boolean result = employees.stream()
        					.filter(t->t.getAge() >= 50)
        					.allMatch(t->t.getAge() >= 55);
    System.out.println(result);
    
    => false
    // 50살이상인 사람들은 모두 55살 이상인가
    ```

    

  - collect( ) : forEach( )와 유사

  - 나이가 50살 이상인 사람들의 연봉을 구해서 List<Integer> 형태의 ArrayList에 저장하자

    ```java
    // List 사용
    List<Integer> tmp = employees.stream()
        					.filter(t->t.getAge() >= 50)
        					.collect(Collectors.toList());
    
    System.out.println(tmp);
    
    => [2500]
    
    
    // Set 사용
    Set<Integer> tmp = employees.stream()
        					.filter(t->t.getAge() >= 50)
        					.collect(Collectors.toCollection((HashSet :: new));
    System.out.println(tmp);     
                                     
    => [2500]           
    
    // Map도 가능
    ```

    