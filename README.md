# Reactive X (RxJava)

> RxJava는 옵저버 패턴을 기반으로 작동하기 때문에 자바 옵저버 패턴에 대한 선행학습은 필수적으로 이뤄져야 한다. 또한 람다식(Lambda Expressions)을 익혀둘 필요가 있다.

### 1. Observer pattern

> 옵저버 패턴에서는 한 객체의 상태가 바뀌면 그 객체에 의존하는 다른 객체들에게 연락이 가고, 자동으로 내용이 갱신되는 방식으로 **'일대다(one-to-many) 의존성'**을 정의한다.

- 아래 UML을 통해 Observer pattern 구현 시, 클래스들의 관계를 알 수 있다. 

![](http://cdn.journaldev.com/wp-content/uploads/2013/07/observer-pattern.png)

##### 1.1. Loose Coupling

>  Loose Coupling 객체 간에 서로 상호작용이 이뤄지지만 서로에 대해 잘 모르는 상태를 말한다.

- 주제가 옵저버에 대해서 아는 것은 옵저버가 특정 인터페이스(Observer interface)를 구현한다는 것 뿐이다.
- 옵저버는 언제든지 새로 추가할 수 있다.
- 새로운 형식의 옵저버를 추가하려고 할 때도 Subject를 전혀 변경할 필요가 없다.
- 주제와 옵저버는 서로 독립적으로 재사용할 수 없다.
- 주제나 옵저버나 바뀌더라도 서로한테 영향을 미치지 않는다.



### 2. Lambda Expressions

> 람다식은 익명 함수를 생성하기 위한 식으로 객체 지향 언어보다는 함수 지향 언어에 가깝다.

##### 2.1. 람다식을 사용하는 이유

- 코드의 간결성
- 컬렉션의 요소를 필터링하거나 매핑하여 원하는 결과를 쉽게 얻을 수 있음
- 람다식의 형태는 매개 변수를 가진 코드 블록이지만, 런타임 시에는 익명 구현 객체를 생성한다.

##### 2.2. 람다식의 기본 문법

```java
/* 기본형 */
(타입, 매개변수) -> { 실행문; }

/* 예시 */
// 1. 기본형
(int a) -> {System.out.println(a);}

// 2. 매개변수 타입 생략
(a) -> {System.out.println(a);}

// 3. 하나의 매개변수만 있다면 괄호() 생략 가능, 하나의 실행문만 있다면 중괄호{}도 생략 가능
a -> System.out.prinlnt(a);

/* 매개변수가 아예 존재하지 않을 경우 */
() -> { 실행문; }

/* 결과값을 리턴해야할 경우 */
(a, b) -> {return a + b;}

/* 중괄호에 리턴문만 있을 경우 */
// 중괄호와 return 생략 가능 
(a, b) -> a + b 
```

```java
// 1. 자바 코드
Runnable runnable = new Runnable(){
  public void run(){ /* 로직 */ }
};
  
// 2. 람다식
Runnable runnable = () -> {  /* 로직 */  };
```



### 3. RxJava

- 비동기, 이벤트 기반 프로그램을 작성하기 위한 라이브러리
- 스트림을 사용한다. 

> RxJava는 자바와 안드로이드를 위한 리액티브 프로그래밍 구현체로서 **함수형 프로그래밍의 영향**을 받았다. 따라서 함수 구성을 선호하며 전역 상태나 부수효과를 피하고 비동기나 이벤트 기반 프로그램을 작성할 때 **스트림 방식**으로 생각한다. 

##### 2.1. RxJava 필수 개념

###### 2.1.1. Observer

> 옵저버는 Observable을 구독하며 '구독자'나 '관찰자' 또는 '리액터'라고 불려지고 있지만, 통상적으로 이 모델은 리액터 패턴을 의미한다.

###### 2.1.2. Observable

- Observable은 emitt(발신, 방출)하는 주체이다.


- RxJava에서 옵저버는 Observable을 구독한다. Observable이 방출하는 하나 또는 연속된 항목에 옵저버는 반응하게 된다.
- Observable은 항목들을 배출하거나 observable의 메서드 호출을 통해 옵저버에게 알림을 보낸다.

![Reactive X에 설명되어 있는 그림](http://reactivex.io/assets/operators/legend.png)







