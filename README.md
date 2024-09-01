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

# Chapter 9
## 테스트 범위 
- 단위 테스트 
- 통합 테스트
  - 개발 완료에 진행하는 최종 테스트 
- 기능 테스트 
  - 고객의 입장에서 요구하는 기능을 올바르게 구현했는지 수행하는 테스트
## 기능 테스트와 E2E 테스트 
- 기능 테스트 (Functional Testing) 
  - 사용자 입장에서 시스템이 제공하는 기능이 올바르게 동작하는지 확인
  - 모든 구성 요소를 하나로 엮어서 진행한다 
  - 브라우저에서 데이터베이스까지 모든 구성 요소를 하나로 엮어서 진행한다 
  - QA 조직에서 수행하는 테스트 
## 통합 테스트 (Integration Testing) 
- 시스템의 각 구성 요소가 올바르게 연동되는지 확인한다
- 소프트웨어의 코드를 직접 테스트한다
## 단위 테스트 (Unit Testing)
- 개별 코드나 컴포넌트가 기대한대로 동작하는지 확인한다
## 테스트 범위에 따른 테스트 코드 개수와 시간
- 기능 테스트나 통합 테스트에서 모든 예외 상황을 테스트하면 단위 테스트는 줄어든다. 
  - 각 테스트가 다루는 내용이 중복되기 떄문이다 
- 가능하면 단위 테스트에서 다양한 상황을 다루고, 통합 테스트나 기능 테스트는 주요 상황에 초점을 맞춰야 한다 
- 피드백이 느려지는 것을 방지
- 테스트 실행 속도가 느려지면 테스트를 작성하지 않거나 테스트 실행을 생략하는 상황이 벌어진다 
- 결국 소프트웨어 품질 저하로 이어진다

# Chapter 10
## 테스트 코드와 유지보수 
- 테스트 코드는 그 자체로 코드이기 떄문에 제품 코드와 동일하게 유지보수 대상이 된다
- 테스트 코드가 깨지는 상황을 방치하면 다음과 같은 문제가 발생한다
  - 실패한 테스트가 새로 발생해도 무감각해진다. 테스트 실패 여부에 상관없이 빌드하고 배포하기 시작한다
  - 빌드를 통과시키기 위해 실패한 테스트를 주석 처리하고 실패한 테스트는 고치지 않는다
- 이런 상황이 발생하면 테스트 코드는 가치를 읽기 시작한다
- 깨진 유리창 이론 
  - 한두 개의 실패한 테스트를 방치하기 시작하면 점점 실패하는 테스트가 증가해서 나중에는 테스트를 고칠 수 없을 지경에 이르게 된다 
  - 깨진 테스트가 있으면 즉시 수정한다
- 