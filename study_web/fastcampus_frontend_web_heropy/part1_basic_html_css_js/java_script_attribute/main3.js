// HTML 요소(Element) 1개 검색/찾기
// let boxEl = document.querySelector('.box');

// HTML 요소에 적용할 수 있는 메소드!
// console.log(boxEl);

// 인수(Arguments)를 추가 가능!
// boxEl.addEventListener(1, 2); (1은 이벤트 발동 조건, 2는 발생 할 이벤트)

// 1 - 이벤트(Event, 상황) :: 사용자가 box요소를 클릭했을 때의 이벤트
// boxEl.addEventListener('click', 2);

// 2 - 핸들러(Handler, 실행할 함수) :: 마찬가지로 클릭 했을s 때의 익명함수를 호출해 줄 수 있음.
// boxEl.addEventListener('click', function() {
//   console.log('Click~!');
// });



// 요소의 클래스 정보 객체 활용!
// boxEl.classList.add('active');
// let isContains = boxEl.classList.contains('active'); // active클래스 포함 여부
// console.log(isContains);

// boxEl.classList.remove('active');
// isContains = boxEl.classList.contains('active');
// console.log(isContains);



// boxEl.addEventListener('click', function() {
//   console.log('Click!');
//   boxEl.classList.add('active'); // 개발자 도구의 Elements보면 active라는 속성이 추가되어 있음.
//   console.log(
//     boxEl.classList.contains('active')
//   );
//   boxEl.classList.remove('active')
//   console.log(
//     boxEl.classList.contains('active')
//   );
// });



// 과정
// // HTML 요소(Element) 모두 검색/찾기
// const boxEls = document.querySelectorAll('.box')
// console.log(boxEls)

// // 찾은 요소들 반복해서 함수 실행!
// // 익명 함수를 인수로 추가!
// boxEls.forEach(function () {});

// // 첫 번째 매개변수(boxEl): 반복 중인 요소.
// // 두 번째 매개변수(index): 반복 중인 번호
// boxEls.forEach(function (boxEl, index) {});

// // 출력!
// boxEls.forEach(function(boxEl, index) {
//   boxEl.classList.add(`order-${index + 1}`)
  
//   console.log(index, boxEl);
// });


// ${} 보간법을 사용하려면 `(그레이브, 백틱)기호로 감싸야 함.
// const boxEls = document.querySelectorAll('.box');

// boxEls.forEach(function(boxEl, index) {
//   boxEl.classList.add(`order-${index + 1}`)
//   console.log(index + 1, boxEl)
// });

const boxEl = document.querySelector('.box');

// Getter, 값을 얻는 용도
console.log(boxEl.textContent); // Box!!

// Setter, 값을 지정하는 용도
boxEl.textContent = 'James';
console.log(boxEl.textContent); // James?