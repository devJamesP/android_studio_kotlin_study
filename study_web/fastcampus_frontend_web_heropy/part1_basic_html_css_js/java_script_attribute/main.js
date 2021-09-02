// (String)문자 데이터
let myName = "JAMES";
let email = 'znzldh12@naver.com';
let hello = `Hello ${email}?!`

console.log(myName); // JAMES
console.log(email); // znzldh12@naver.com
console.log(hello); // Hello znzldh12@naver.com


// (Number)숫자 데이터
let number = 123;
let opacity = 1.56;

console.log(number); // 123
console.log(opacity); // 1.56


// Boolean(불린 데이터)
let checked = true;
let isShow = false;

console.log(checked); // true
console.log(isShow); // false


// Undefined
// 값이 할당되지 않은 상태를 나타냄
let undef; // 할당 되지 않음 
let obj = {
  abc: 123
}; // 객체 생성(abc속성)

console.log(undef); // undifined
console.log(obj.abc); // 123
console.log(obj.xyz); // undifined


// null
// 어떤 값이 의도적으로 비어있음.
let empty = null;
console.log(empty); // null


// Object(객체 데이터)
// 여러 데이터를 Key:Value 형태로 저장 { }
let user = {
  // Key : Value,
  name: 'James',
  age: 100,
  isValid: true
};

console.log(user.name); // James
console.log(user.age); // 100
console.log(user.isValid); // true


// Array(배열 데이터)
// 여러 데이터를 순차적으로 저장함 [ ]
let fruits = ['Apple', 'Banana', 'Cherry'];

console.log(fruits[0]); // Apple
console.log(fruits[1]); // Banana
console.log(fruits[2]); // Cherry


// 간단한 연산
let a = 12;
let b = 4;
console.log(a + b);
console.log(a - b);
console.log(a * b);
console.log(a / b);


// let : 재할당 가능 const: 재할당 불가능
let lA = 123;
console.log(lA);

lA = 999;
console.log(lA);


const cA = 123;
console.log(cA);

// cA = 999; // TypeError: Assignment to constant variable.


// 예약어 : 특별한 의미를 가지고 있어, 변수나 함수 이름 등으로 사용할 수 없는 단어 :: Reserved Word
// let this = 'Hello'; // SyntexError