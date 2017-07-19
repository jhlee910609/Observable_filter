# RxJava - operator

> Observable을 생성하는 다양한 연사자가 있다. 
> 용도에 맞는 Observable을 생성하기 위해 자주 사용되는 연산자의 특징을 알아두자.

### 1. Creating Observable

##### 1.1. `.create():`
- 함수를 통해 `Observable`을 생성한다.
  - `onNext();`, `onError();`, `onCompleted();`를 상황에 맞게 호출한다.

![](http://reactivex.io/documentation/operators/images/create.c.png)

##### 1.2.`.just();`

- 특정 아이템을 발행하는 `Observable`을 생성한다.
- `.from();` 메소드와 비슷해 보이지만 단 하나의 아이템을 가진 그대로 발행한다는 점에서 다르다. 
- 이미지, 영상 등 **'데이터 이외의 객체'**를 Observable에 의해 발행받길 원할 때 주로 사용한다.  ![](http://reactivex.io/documentation/operators/images/just.c.png)


##### 1.3. `.from();`

- Observables로 작업 시, Observables 이를 통해 데이터 집합을 사용하여 데이터 스트림을 관리할 수 있다.
- `.fromArray();` 메소를 통해 데이터 셋을 넘겨줄 수 있다.
  ![](http://reactivex.io/documentation/operators/images/from.c.png)

### 2. Transforming Observable

##### 1.1. `.map();`

- Observable에 의해 발행된 각각의 아이템을 작성한 함수에 맞게 변형한다.
- 작성한 함수를 통해 1:1 

> 예제 

```java
// 1. test용 observable생성 
Observable<Integer> observable = Observable.create(emitter -> {
            for (int i = 0; i < 12; i++) {
                emitter.onNext(i);
                Thread.sleep(1000);
            }
            emitter.onComplete();
        });

public void doMap(View view) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(item -> item.equals("May") ? false : true)
          
          		// 2. map을 활용하여 Observable이 발행한 아이템 변형 
          		// -> 결과적으로 RecyclerView Holder의 TextView에 '>> item <<' 로 .setText(); 된다. 
                .map(item -> ">> " + item + " <<")
                .subscribe(
                        item -> {
                            data.add(item);
                            adapter.notifyItemInserted(data.size() - 1);
                        },
                        error -> Log.e("Error : ", error.getMessage()),
                        () -> Log.i("Complete", "Successfully completed!")
                );
    }
```



##### 1.2. `.flatMap()`

- 아이템 배열(a series of items), collection 등을 Observable로 변환시킬 때 주로 사용한다.
- 작성한 함수를 통해 Observable<T> 을 Observable<R> 로 변환할 수 있도록 도와준다.

![](http://reactivex.io/documentation/operators/images/flatMap.c.png)

> 예제

```java
List<String> data = new ArrayList<>();
Observable<Integer> observable;
String months[] = {};

public void doFlatmap(View view) {
    observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
      		// 1. months[item]를 차례로 꺼내 new String[]{/* 아래 선언한 형태 */} 배열을 만든다. 
            .flatMap(item -> Observable.fromArray(new String[]{"name : " + months[item], "code : " 				+ item}))
            .subscribe(
                    item -> {
                      // 2. OnNext(); - 변형한 Strin[]을 data에 저장한다. 
                        data.add(item);
                        adapter.notifyItemInserted(data.size() - 1);
                    },
                    error -> Log.e("Error", error.getMessage()),
                    () -> Log.i("Complete", "Successfully complete")
            );
}
```