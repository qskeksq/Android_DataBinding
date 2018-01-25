# Android Data Binding 

## 1. 소개

#### 정의

- Data Binding은 데이터 제공자와 데이터 사용자 사이의 데이터를 동기화하는 기술. 작업 없이 데이터 변화를 제공자와 사용자에게 반영 할 수 있다.
- 데이터 바인딩 이란 두 데이터 혹은 정보의 소스를 모두 일치시키는 기법이다. 즉 화면에 보이는 데이터와 브라우저 메모리에 있는 데이터를 일치시키는 기법이다.
- Data binding is a general technique that binds data sources from the provider and consumer together and synchronizes them. This is usually done with two data/information sources with different languages as in XML data binding. In UI data binding, data and information objects of the same language but different logic function are bound together (e.g. Java UI elements to Java objects)
- In a data binding process, each data change is reflected automatically by the elements that are bound to the data. The term data binding is also used in cases where an outer representation of data in an element changes, and the underlying data is automatically updated to reflect this change. As an example, a change in a TextBox element could modify the underlying data value


#### 목적 & 특성

- 다른 작업 없이('new'를 통한 객체 생성 없이, 의존성을 생성할 필요 없이) 데이터의 변화를 동기화하므로 UI 로직과 비즈니스 로직 분리 도움이 된다.
- Data Binding의 사용 목적은 "표현식"을 통해 코드를 간결화하고 가독성을 높히는 것
- 안드로이드에서 번거로운 작업 중 하나는 xml에서 view를 생성하면 자바 쪽에서 findViewById()메소드로 일일히 잡아주어야 하는 점이다. 데이터 바인딩은 그런 번거로운 작업을 덜어주고 자바 쪽 코드를 상당히 직관적으로 만들어준다
- 단방향 데이터 바인딩은 데이터와 템플릿을 결합하여 화면을 생성한다. 반면 양방향 데이터 바인딩을 데이터의 변화를 감지해 템플릿과 결합하여 화면을 갱신하고 화면에서의 입력에 따라 데이터를 갱신한다. 즉, 데이터와 화면 사이의 데이터가 계속해서 일치하게 되는 것이다.
- 조건
    - Android 2.1(API레벨 7) 이상
    - Android Plugin for Gradle 1.5.0-alpha1 이상
    - Android Studio 1.3 이상
- 안드로이드 MVVM은 데이터바인딩을 통해 구현


## 2. 설정

- Gradle

    ```javaScript
    android {
        //...
        dataBinding {
            enabled = true
        }

    }
    ```

- Check List
    - Are type attributes valid reference to data object? type="my.package.Class"
    - Are the binding valid? name="client" -> "@{client.field}"
    - Are data fields accessible? public or encapsulated with getters
    - Are field names in java and the xml file matching, check for typos
    - If you have renamed the xml file, make sure you also update the Binding objects. OldNameBinding -> NewNameBinding
    - Clean Project



## 3. 적용
    
    layout으로 xml을 감사면 자동으로 바인딩 클래스를 만들어준다. 즉, xml에 필요한 데이터를 바인딩 클래스에 넘겨주면 바인딩 클래스가 알아서 데이터 바인딩을 진행한다. 데이터가 업데이트 될 때마다 자동 생성된 바인딩 클래스틑 View를 업데이트 하기 위해 setter 메소드를 호출한다

- xml에서 추가
    - data, variable를 통해 바인딩 클래스와 연결
    - @{}를 통해 바인딩 클래스에서 접근
- java 변경 사항
    - 자동 생성되는 '***Binding' 클래스 바인딩 생성
    - setVariable()
    - MVVM
 
### (1) POJO Binding

- xml
    ```html
    <data>
        <variable
            name="item"
            type="com.example.mac.mvvm.Item" />
    </data>
    ```
    ```html
    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        //...
        android:text="@{item.name}"
        //...                       />
    ```
- java
    ```java
    ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    binding.setVariable(BR.item, new Item("name"));
    ```

### (2) POJO 변경시 UI 변경(NotifyChanged)

#### A. POJO 클래스 BaseObservable 상속

```java
public class Item extends BaseObservable{
    private String name;

    public Item(String name) {
        this.name = name;
    }

    @Bindable
    public String getName() {
        return name;
    }
    // BR.name은 Bindable을 해야 BR에 등록이 되는 듯 하다
    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
}
```

#### B. ObservableField<>
    ```java
    public class Item2{
        public final ObservableField<String> name = new ObservableField<>();
    }
    ```

#### C. ObservableArrayMap<>    

- 동적으로 데이터가 변할 경우 

```html
<import type="android.databinding.ObservableArrayMap" />
<import type="com.example.mac.mvvm.Item" />
<variable
        name="itmMap"
        type="ObservableArrayMap&lt;String, String&gt;" />
```
```html
<Button
    android:text="@{itemMap.name}" />
```

#### D. ObservableArrayList<>
- UI에 바인딩되어 있고 ObservableArrayList를 통해서 데이터 객체의 속성이 변경되면 UI가 자동으로 업데이트 된다. 즉, 데이터가 변경될때마다 데이터를 set할 필요가 없다.
- 동적으로 데이터가 변할 경우
    ```html
    <import type="android.databinding.ObservableArrayList" />  // 어떤 종류의 컬렉션을 사용할 것인지 지정된 databinding 클래스에서 선택
    <import type="com.example.mac.mvvm.Item" />                // 어떤 데이터 객체를 사용할 것인지
    <variable                                                  
            name="itemList"
            type="ObservableArrayList&lt;Item&gt;" />          // < > 를 나타내기 위해 &lt; 사용
    ```
- 일반 위젯
    ```html
    <Button
        android:text="@{itemList[0]}" />
    ```

- RecyclerView
    ```html
    <layout 
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:bind="http://schemas.android.com/tools">              // 추가로 namespace 설정을 해줘야 함
    <android.support.v7.widget.RecyclerView
            bind:item="@{itemList}" />
    ```

### (3) 이벤트 바인딩

- View : xml

    ```html
    <data>
        <variable
            name="activity"
            type="com.example.mac.mvvm.MainActivity" />
    </data>
    ```
    - 메서드 참조
        - 매개변수, 리턴타입 모두 일치해야 함
        - 데이터가 바인딩 될 때 실제 리스너 구현. 따라서 데이터가 바인딩 되는 시점에 매개변수가 정의되어 있어야 한다. 바인딩 되는 시점에 데이터가 구현되어 있지 않으면 동작하지 않지만 데이터가 정의되어 있다면 나은 성능을 기대할 수 있음.
        ```html
        <Button
            android:onClick="@{(v)->activity.onButtonClick(v)}"
            // ...                                           />
        ```

    - 리스닝 바인딩
        - 리턴 타입만 일치해도 됨
        - 매개변수를 써도 되고 안 써도 됨
        - 이벤트 발생시 리스너 구현. 바인딩 되는 시점에서 데이터를 정의할 수 없는 비동기 통신 데이터들에 적합.
        ```html
        <Button
            android:onClick="@{activity::onButtonClick}"
            // ...                                           />
        ```

- java
    ```java
    ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    binding.setVariable(BR.activity, this);
    ```

    ```java
    public void onButtonClick(View view){
        Toast.makeText(this,"Button Click",Toast.LENGTH_SHORT).show();
    }
    ```

### (4) import 사용하기

- 기본

    ```html
    <data>
        <import type="android.view.View"/>
        <import type="java.util.List"/>
        <variable name="userList" type="List&lt;Object&gt;"/>
    </data>

    <TextView
    android:text="@{user.lastName}"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:visibility="@{user.isAdult ? View.VISIBLE : View.GONE}"/>
    ```

- 정적 함수, 메소드

    ```html
    <data>
    <import type="com.example.MyStringUtils"/>
    <variable name="user" type="com.example.User"/>
    </data>
    …
    <TextView
    android:text="@{MyStringUtils.capitalize(user.lastName)}"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"/>
    ```

### (4) @BindingAdapter

    xml 상에서 set*** 할 수 있으나 코드 상에서 없는 경우는 BindingAdapter 속성을 지정하고 xml 상에서 사용할 수 있다

    ```java
    @BindingAdapter({"bind:font"})
    public static void setFont(TextView textView, String fontName) {
        textView.setTypeface(Typeface.createFromAsset(textView.getContext().getAssets(), "fonts/" + fontName));
    }
    ```

    ```java
    @BindingAdapter({"bind:imageUrl", "bind:error"})
    public static void loadImage(ImageView imageView, String url, Drawable errorDrawable) {
        // ImageUtil.loadImage(imageView, url, errorDrawable);
    }
    ```

    ```html
    <TextView
        android:id="@+id/tvUserTime"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@{user.timeDate}"
        />
    ```

### (5) @BindingConversion

    xml에 들어오는 값을 자동으로 변환시켜 주거나 자동으로 속성이 적용되도록 함. 반응형 UI를 쉽게 구현할 수 있다

- @BindingConversion에서 Date타입이 들어오면 이를 String으로 변경해서 리턴. xml에서 text에 Date타입의 필드를 넣어주면 알아서 변경되서 값이 변경

    ```java
    @BindingConversion
    public static String convertDateToDisplayedText(Date date) {
        return new SimpleDateFormat("yyyy.MM.dd").format(date);
    }
    ```

    ```html
    <TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:visibility="@{user.age > 20}"
    />

    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:visibility="@{user.loaded}"
        >
    ```



### (6) 레이아웃 재활용

    커스텀뷰를 만들지 않고 inlcude를 활용하여 여러 뷰를 재활용 할 수 있다

- 재활용 레이아웃

    ```html
    <layout
        xmlns:android="http://schemas.android.com/apk/res/android">

        <data>
            <variable name="count" type="int"/>
            <variable name="title" type="String"/>
        </data>

        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:orientation="vertical">

            <TextView
            style="@style/CustomText_Subhead"
            android:text="@{Integer.toString(count)}"
            android:textStyle="bold"
            />

            <TextView
            style="@style/CustomText_Body"
            android:text="@{title}"
            android:textColor="@color/txt_midiumgray"/>

        </LinearLayout>

    </layout>
    ```

- 재활용

    ```html
     <include
        layout="@layout/sns_counter"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        app:count="@{user.postCount}"
        app:title="@{@string/post}"/>

    <include
        layout="@layout/sns_counter"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        app:count="@{user.followerCount}"
        app:title="@{@string/follower}"/>

    <include
        layout="@layout/sns_counter"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        app:count="@{user.followingCount}"
        app:title="@{@string/following}"/>
    ```


## 4. Example

### (1) RecyclerView

- xml

    ```html
    <import type="android.databinding.ObservableArrayList" />
    <import type="com.example.mac.mvvm.Item" />
    <variable
        name="itemList"
        type="ObservableArrayList&lt;Item&gt;" />
    <android.support.v7.widget.RecyclerView
            android:id="@+id/recyclerView"
            // ...
            bind:item="@{itemList}" />
    ```

- @BindAdapter

    ```java
    @BindingAdapter("bind:item")
    public static void bindItem(RecyclerView recyclerView, ObservableArrayList<Item> item) {
        DumAdapter adapter =(DumAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setItem(item);
        }
    }
    ```

- Adapter

    - onCreateViewHolder

        ```java
        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            ItemBinding binding = ItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new Holder(binding);
        }
        ```

    - onBindViewHolder

        ```java
        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.binding.setItem(list.get(position));
            // or holder.binding.setVariable(BR.item, list.get(position));
        }
        ```

    - Holder

        ```java
        public class Holder extends RecyclerView.ViewHolder {
            ItemBinding binding;
            public Holder(ItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
        ```

- RecyclerView 바인딩 클래스

    ```java
    private void bindRecycler() {
        // 어댑터
        adapter = new DumAdapter();
        // 어댑터 세팅
        binding.recyclerView.setAdapter(adapter);

        // 데이터
        list = new ObservableArrayList<>();
        // 데이터 세팅
        binding.setItemList(list);
    }
    ```



참고
- http://heepie.tistory.com/220?category=730950
- http://gun0912.tistory.com/71
- https://brunch.co.kr/@oemilk/107
- https://medium.com/@futureofdev/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-what-is-databinding-recyclerview-e67abb855788
- https://developer.android.com/topic/libraries/data-binding/index.html#build_environment
- https://medium.com/@devyunsy/data-binding-guide-3-263ec47dbc8
