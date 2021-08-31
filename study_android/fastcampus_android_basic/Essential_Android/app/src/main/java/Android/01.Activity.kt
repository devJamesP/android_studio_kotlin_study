package Android

/*
01. Activity
- Life Cycle(수명 주기)
onCreate -> 보통 앱 최초 실행 시 한번만 호출 됨
         -> activity를 만들때 단 한번만 하면 되는 작업들은 여기에서 해주면 됨
onStart

onResume -> 다시 앱으로 돌아올 때 무조건 호출 됨
         -> activity가 다시 호출될 때 하면 되는 작업들을 여기에서 해줌

onPause -> 앱의 일부분이 보이지 않으면 호출 됨

onStop -> 앱이 완전히 보이지 않으면 호출 됨
       -> 잠깐 다른 앱으로 넘어갔을 때 기존에 작업을 임시로 저장 시

onDestroy
onRestart


 */