// 1.the quick brown fox를 camelCase로 표기
// const camelCase = 'TheQuickBrownFox';
// const boxEl = document.querySelector('.box');
// boxEl.textContent = camelCase;

// 2. Banana를 콘솔 출력하기
let fruits = ['Apple', 'Banana', 'Cherry'];
console.log(fruits[1]);

// 3. Boolean데이터에서 거짓을 의미하는 데이터
const booleanFalse = false;

// 4. 값이 의도적으로 비어있음을 의미하는 데이터
const nullData = null;

// 5. {}데이터의 종류는? 오브젝트(객체)데이터

// 6. let obj = { abc:123}; console.log(obj.xyz); 콭솔 출려될 값?
// undefined 출력됨.

// 7. 값을 재할당 할 수 없는 변수 선언 키워드는 : const

// 8. 함수에서 값(데이터)을 반환하기 위해 사용하는 키워드는? : return 

// 9. sum(2, 4); 위 함수 호출에서 2, 4를 무엇이라 하는가? : Arguments(인수)

// 10. function sum(a, b) { return a + b; } 함수 호출에서 전달받은 인수를 함수 내부로 전달하기 위한 변수를 무엇이라 하는가? : Parameter(매개변수)

// 11. 이름이 없는 함술를 무엇이라 하는가? : anonymous function(익명 함수)

// 12. hello 이름의 함수 표현을 작성하고 호출하기!
function hello() {
  console.log('hello!')
};

hello();

// 13. const user = { getName: function() {} } getName과 같이, 함수가 할당된 객체 데이터의 속성(property)을 무엇이라 하는가? : Method(메소드)

// 14. 조건이 참(true)인 조건문을 작성하시오!
if (true) {
  console.log('조건이 참입니다.');
} else {
  console.log('조건이 거짓입니다.');
}

// 15. 가져온 js파일을 HTML 문서 분석 이후에 실행하도록 지시하는 HTML 속성(Attrubute)은? : defer

// 16. <div class="box">Box!!</div>의 요소의 내용(Content)을 콘솔 출력!!
// const boxEl = document.querySelector('.box');
// console.log(boxEl);

// 17. 값(데이터)을 재할당할 목적의 변수 선언 키워드는? : let

// 18. const boxEl = document.querySelector('.box');의 클릭 이벤트를 추가해 클릭 시 'Hello~'를 콘솔 출력
const boxEl = document.querySelector('.box');
boxEl.addEventListener('click', function() {
  console.log('Hello~');
});

// 19. <div>1</div> <div>2</div> 해당 요소에 JS로 class="hello"를 추가하시오ㄹ
const boxEls = document.querySelectorAll('div');
boxEls.forEach(function(boxEl) {
  boxEl.classList.add('hello');
});
  
// 20. 'James'.split('').reverse().join('');와 같음 메소드를 이어 작성하는 방법 : Method Chaining(메소드 체이닝)

// 21. const boxEl = document.querySelector('.box');의 boxEl 요소에 HTML 클래스 속성의 값으로 'active'가 포함되어 있으면 콘솔에 '포함됨!'을 출력
let isActiveContains = false;
const boxEls2 = document.querySelectorAll('div')
// 1번째 요소는 active 포함, 2,3번째 요소는 active 포함안함.
boxEls2[0].classList.add('active');
boxEls2.forEach(function(boxEl){
  isActiveContains = boxEl.classList.contains('active');
  if (isActiveContains) {
    console.log('포함됨');
  } else {
    console.log('포함안됨');
  }
});