# Chapter 3
매달 비용을 지불해야 사용할 수 있는 유료 서비스 기획
- 서비스를 사용하려면 매달 1만 원을 선불로 납부한다. 납부일 기준으로 한 달 뒤가 서비스 만료일이 된다
- 2개월 이상 요금을 납부할 수 있다
- 10만 원을 납부하면 서비스를 1년 제공한다

- Tip
  - 구현하기 쉬운 것부터 먼저 테스트 
  - 예외 상황을 먼저 테스트 

- Step 1
  - 1만 원을 납부하면 한 달 뒤 같은 날을 만료일로 계산하는 것이 가장 쉬움

- example 4 
  - 첫 납부일이 2019-01-31이고 만료되는 2019-02-28에 1만 원을 납부하면 다음 만료일은 2019-03-31이다

- example 5
  - 첫 납부일이 2019-01-30이고 만료되는 2019-02-28에 1만 원을 납부하면 다음 만료일은 2019-03-30이다

- example 6
  - 첫 납부일이 2019-5-31이고 만료되는 2019-06-30에 1만 원을 납부하면 다음 만료일은 2019-07-31이다

- example 7
  - 2만 원을 지불하면 만료일이 두 달 뒤가 된다
  - 3만 원을 지불하면 만료일이 석 달 뒤가 된다 

- example 8
  - 첫 납부일이 2019-01-31이고 만료되는 2019-02-28에 2만원을 납부하면 다음 만료일은 2019-04-30이다

- example 9
  - 10개월 요금을 납부하면 1년 제공

# Chapter 4
- p100 사용자에게 제공할 기능을 구현하려면 기능을 크게 두 가지로 나누어 생각해 볼 수 있다
  - 그것은 바로 입력과 결과이다
  - 입력은 보통 메서드의 파라미터로 전달한다
  - 결과는 리턴 값이다
- p104 이름은 설계에서 매우 중요하다
  - 레거시 코드를 보면 이름에서 기대하는 것과 다르게 동작하는 코드를 종종 만나게 된다
  - 개발자를 속일 뿐만 아니라 코드를 분석하는 시간을 증가 시켜 코드 수정을 어렵게 만드는 원인이 된다
- p105 필요한 만큼 설계하기
  - TDD는 미리 앞서서 코드를 만들지 않는다
