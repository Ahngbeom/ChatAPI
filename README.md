# MBTI Chat

MBTI 주제를 가진 유저들 간 채팅방을 개설 또는 입장하여 소통하는 웹 어플리케이션

### 기술 스택

- Spring Boot
- Spring WebSocket
- Spring Security
- MariaDB
- JPA
- Thymeleaf
- Bootstrap 5
- JavaScript & JQuery

### 기능 스택

- 회원가입 API
    
    JS, JQuery를 통해 회원가입 Form에서 발생하는 이벤트(click, input, change)에 대한 함수를 구현하여 동적인 웹 페이지를 구현
    
    - JS 정규식을 통해 ID 문법을 검사. 
    Ajax와 DB 회원 테이블을 탐색하여 ID를 중복체크하는 API를 ID 유효성 검사.
    - JS 정규식을 통해 PW가 정해진 규칙에 부합하는지 검사.
    PW 재확인 기능까지 구현
    Spring Security의 Interface 객체인 PasswordEncoder의 구현체를 BCryptPasswordEncoder 객체로 지정하여 Hash 함수를 활용한 PW 암호화 방식 채택.
    암호화된 PW를 DB에 저장.
    - JS 정규식을 통해 Nickname 문법 검사.
    Nickname 또한 유일성이 보장되어야하기 때문에 ID와 동일한 방식으로 중복 검사 진행.
    - 회원의 권한(ADMIN | MANAGER | USER)을 설정하기 위한 다중 선택 체크박스 Form
    선택한 권한은 별도의 유저 권한 테이블에 저장된다.
    일반 유저 권한이 아닌 관리자 관련 권한에 대해 기존 관리자의 확인 절차 기능 구현 계획 중…
- 로그인 & 로그아웃 API
    - UserDetailsService 의 loadUserByUsername 메소드를 오버라이딩하여 DB에 저장되어있는 회원 데이터와 대조하여 유효성을 검증한다.
    - AuthenticationSuccessHandler 인터페이스의 onAuthenticationSuccess 메소드를 오버라이딩하여 로그인 성공 후 처리 방식 설계.
    RedirectStrategy 객체를 통해 로그인을 요구한 유저의 권한에 따라 특정 URL로 리디렉션.
    - Spring Security - HttpSecurity의 rememberMe 메소드를 호출하여 로그인 정보 저장 기능 활성화.
    remember-me 쿠키의 토큰 값을 통해 유효성(만료시간, ID, PW, 토큰 키)을 검증한 후, 자동 로그인 과정을 수행한다.
    - Spring Security - HttpSecurity의 logout 메소드를 호출하여 로그아웃 기능 활성화.
    단순하게 로그아웃 URL을 특정. 로그아웃 시 JSESSIONID, remember-me 쿠키 모두 삭제.
- MBTI 등록 API
    - 사용자는 회원가입을 한 후, 4개의 설문 항목만으로 자신의 MBTI 결과를 등록할 수 있다.
    (공식 MBTI 테스트는 저작권 문제로 인해 함부로 카피해오면 안될 것 같다.)
    - 사용자는 MBTI를 무제한 등록할 수 있다.
    - 간소화된 4개의 질문으로 MBTI 결과 도출
        - Focused(초점, 태도) - E, I
        - Recognition(인식) - S, N
        - Decision(판단, 결정) - T, F
        - Fulfillment(이행, 생활) - J, P
        
        질문에 대한 답은 2개의 문항이 주어지고, 하나의 문항만을 선택할 수 있다.
        
    - 사용자가 선택한 문항을 종합하여 MBTI 결과 코드를 도출하여 공식 웹사이트에서 해당 코드에 대한 정보를 추출해온다.
    ajax를 통해 해당 코드의 정보가 포함되어있는 페이지로 접근하여 적절한 정보들만을 추출 후, 서버에게 전송하여 사용자의 MBTI로 등록한다.
    - MBTI 등록은 무한히 시도할 수 있고, 모든 테스트의 결과가 DB에 저장된다.
    - *구현 준비 중)* DB에 저장된 테스트 결과에 대한 차트, 그래프를 확인할 수 있다.
    - *구현 준비 중)* 사용자는 대표 MBTI를 등록할 수 있다.
        - 대표 MBTI 는 1개만 지정할 수 있다.
        - 대표 MBTI 변경 가능 기한 일주일이다.
- 채팅방 CRUD API
    - 채팅방 등록 시, 채팅방 이름은 중복되어서는 안된다.
    - 채팅방의 설명을 선택적으로 기입할 수 있다.
    - 채팅방의 특정 MBTI 코드가 등록된 사용자만이 입장할 수 있도록 제한할 수 있다.
        - 채팅방에 대한 테이블의 컬럼은 채팅방명, 채팅방소개, 채팅방 설립자, 개설일자로 이루어져 있다.
        - 채팅방의 허용된 MBTI 코드는 MBTI 정보가 담긴 테이블과 채팅방에 대한 테이블을 참조하는 별도의 테이블로 관리된다.
    - 사용자는 개설된 전체 채팅방 목록과 자신이 입장되어져있는 채팅방 목록을 확인할 수 있다.
        - 채팅방 목록은 클라이언트가 퇴장하지않는 한 그대로 유지되어있어야한다.
- 채팅방 입장 및 퇴장 API
    - StompEndpointRegistry 객체에 사용자가 WebSocket에 접속할 수 있는 EndPoint 등록
    - 보안을 위해 StompEndpointRegistry 객체에 지정된 origin 목록만을 허용하도록 설정.
    현재는 로컬환경에서만 작업 중이기에 ‘http://localhost:8080’ origin만을 허용하도록 설정되어있다.
    - withSockJS를 통해 소켓을 등록. 만약 브라우저에서 WebSocket을 지원하지 않을 경우 FallBack 옵션 활성화.
    - SockJS 통해 서버의 WebSocket 지원 여부, Cookies 필요 여부, CORS를 위한 Origin 정보 등을 응답으로 전달받는다.
    - 특정 클라이언트가 다른 클라이언트에게 메시지를 라우팅할 때 사용하는 브로커 구성
    특정 URL 패턴의 요청 메시지를 핸들러로 라우팅하여 해당 URL 패턴에 가입한 모든 클라이언트에게 메시지를 브로드캐스팅한다.
    - 채팅방 입장 시
        - EndPoint를 통해 WebSocket 연결
        - STOMP를 통해 입장한 채팅방의 고유 URL을 구독하여 해당 채팅방으로부터 전달되는 메시지를 모두 수신한다.
        - 유저 정보 테이블과 채팅방 테이블을 참조하는 별도의 테이블에 입장한 유저의 정보가 저장된다.
    - 채팅방 퇴장 시
        - 유저 정보 테이블과 채팅방 테이블을 참조하는 별도의 테이블에서 퇴장한 유저의 정보가 삭제된다.
        - STOMP를 통해 입장한 채팅방의 고유 URL을 구독 해지하여 해당 채팅방으로부터 전달되는 메시지를 모두 수신하지 않는다.
        - 연결되어있던 WebSocket을 해제한다.
- 채팅방 메시지 수신 및 발신 API
    - 사용자가 특정 채팅방에서 메시지를 보낼 경우, Spring Controller에 등록된 해당 채팅방 전용 발신 URL로 전달.
    - 서버는 수신 메시지를 발신자, 메시지 내용, 타입이 담긴 클래스로 가공하여 해당 채팅방에 접속되어있는 모든 사용자에게 메시지를 브로드캐스팅.
- 채팅방 메시지 기록
    - Message 클래스안의 메시지 정보를 담아 Entity 객체로 변환하여 DB에 저장.
    - 사용자가 채팅방을 닫았다가 다시 열 때 DB에 저장되어있는 메시지 기록을 추출.
- *구현 준비 중)*
    - Manager 권한
        
        Manager 권한을 가지기 위해서는 다음과 같은 조건이 주어진다.
        
        - 가입 후 1개월이 지나야한다.
        - 채팅방에서 대화 횟수가 100회 이상이어야한다. (반복 문자, 글을 전송하는 악의적인 방법을 수행할 경우 관리자가 제재)
        - 위 두 조건이 모두 만족한다면, 관리자에게 Manager 권한을 요청할 수 있다.
        관리자는 권한 요청에 대해 수락 및 거부할 수 있다.
