# javatalk
- 개요 : swing을 이용해 만든 간단한 소켓채팅 애플리케이션
- 개발인원 : 1명
- 개발기간 : 일주일
- 사용기술 : JAVA
- 설명 : 
java 과정의 마지막 과제로 만들어 본 프로젝트 입니다. 
학습 과정 중에 배웠던 개념들을 최대한 활용하고자 노력했습니다. 
서버와 클라이언트는 소켓 통신을 통해 연결되고 message 객체를 주고 받을 수 있습니다. 
GUI는 구성을 위해 swing을 사용했고 외부 API(네이버 백과사전 검색)를 이용하기도 했습니다.
- 구현기능 :
	- 서버 접속시 중복 닉네임 체크 / 중복일 때 에러 페이지 출력
	- 입장 / 퇴장 시 안내 메세지 출력
	- 프로필 이미지(아바타) 설정 가능
	- 현재 접속 인원 리스트 출력 (채팅창 상단 메뉴 모양 클릭시 생성)
	- 접속 인원 리스트 더블클릭 혹은 '/w' 명령어 직접 입력을 통해 귓속말 전송
	- 채팅창 오른쪽 아래에 이미지 아이콘 클릭시 이미지 파일 전송 가능
	- 채팅창 오른쪽 아래에 저장 아이콘 클릭시 채팅로그 저장
	- '#' 명령 이용해서  네이버 백과사전 검색 (예 : #서울)
	- 서버 종료시 네트워크 오류 출력
- 클래스 설명
	- javatalk.client.ClientLauncher : 클라이언트 실행
	- javatalk.client.Listener : 서버로 부터 전송되는 메세지 수신
	- javatalk.client.Sender : 서버로 메세지 송신
	- javatalk.frame.AppFrame : 애플리케이션 실행 창
	- javatalk.frame.ChatPanel : 채팅 화면
	- javatalk.frame.ErrorPanel : 에러 화면
	- javatalk.frame.LoginPanel : 최초 접속 화면
	- javatalk.frame.ProfileFrame : 프로필(아바타) 선택 창
	- javatalk.model.ChatCommand : 채팅 명령어 열거 타입
	- javatalk.model.FilePath : 이미지 파일 저장 위치 열거 타입
	- javatalk.model.Message : 서버와 송수신 하는 메세지 객체를 만드는 클래스
	- javatalk.model.TypeOfMessage : 메세지 종류 열거 타입
	- javatalk.model.User : 사용자 정보를 담는 클래스
	- javatalk.server.Handler : 서버측으로 송신되는 메세지를 수신하고 메세지 타입에 따라 작업 후 다시 클라이언트로 전달
	- javatalk.server.ServerLauncher : 서버 실행
	- javatalk.util.FileChooserUtil : 파일 탐색기
	- javatalk.util.FileSaveLoadUtil : 파일 저장 / 로드
	- javatalk.util.HTMLMaker : 채팅 대화창에 표시되는 메세지의 표시 양식을 지정하기 위한 HTML 코드 생성기
	- javatalk.util.NaverSearching : 네이버 검색 API
	- javatalk.util.UserList : 접속자 리스트 관리
- 느낀점 :
생소한 swing을 이용해 프로젝트를 수행하다 보니 모르는 부분이 많아 프로젝트 수행어 어려운 점이 많았습니다.
특히 책이나 웹사이트를 뒤져도 필요한 개념이나 코드를 찾기 힘들어 작은 기능을 구현하는데 조차 많은 시간이 소모 되었습니다.
하지만 새로운 개념을 알아가며 하나씩 상상하던 기능을 구현할 때 마다 성취감을 느낄 수 있었고 그 과정에서 재미를 느끼기도 했습니다.
그리고 단순히 기능 구현에만 신경스지 않고 조금 더 개끗하고 가독성 높은 코드를 만들고자 노력했습니다.
이런 경험이 다음에 새로운 프로젝트를 시작했을 때 도움이 될 수 있을 것이라고 생각합니다.  
- 실행 방법 : 
jar 파일과 images 폴더를 다운 받아야 합니다. 서버 jar 파일을 먼저 실행시킨 이후 client jar 파일을 실행시키면 결과를 확인해 볼 수 있습니다.