// 함수 선언
function helloFunc() {
  // 실행 코드
  console.log(1234);
}

// 함수 호출
helloFunc(); // 1234


function returnFunc() {
  return 123;
}

const returnA = returnFunc();
console.log(returnA); // 123


// 함수 선언
function sum(a, b) { // a와 b의 매개변수 (Parameters) 
  return a + b;
}

// 재사용!
const a = sum(1, 2); // 1과 2는 인수 (Arguments)
const b = sum(7, 12);
const c = sum(2, 4);

console.log(a, b, c);


// 기명 함수 (함수를 선언한다. 라고 함)
function hello() {
  console.log('Hello~');
}

// 익명 함수 (함수를 표현한다. 라고 함)
const world = function () {
  console.log('World~');
}

// 함수 호출
hello(); // Hello~
world(); // World~


const james = {
  name: 'James',
  age: 100,
  // 메소드(Method) 함수의 표현!
  getName: function () {
    return this.name;
  }
};

// 함수 실행!
const hisName = james.getName();
console.log(hisName); // James

console.log(james.getName()); // James :: 이걸 더 추천


// 조건문
let isShow = true
let checked = false;

if (isShow) {
  console.log('Show!'); // Show!
}

if (checked) {
  console.log('Checked!');
}


if (isShow) {
  console.log('Show!');
} else {
  console.log('Hide?');
}

