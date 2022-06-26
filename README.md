# TimeCapsule Backend
타임캡슐 서버 레포

### Branch Naming Convention

### Commit Convention

커밋 태그는 모두 소문자로 한다. 태그 뒤에 띄어쓰기 없이 :를 붙이고 : 뒤에 띄어쓰기를 사용한다.
> 예시)
>
> feat: 로그인 기능 구현

### 코드컨벤션을 위한 IntelliJ 설정 (필수)

- 이 프로젝트에서 코드컨벤션은 Google Java Style Guide를 따르되 몇가지를 수정한다.
    - 인덴트 4 spaces.
    - Max line length는 114.
    - Javadoc 작성 강제 x

- **IntelliJ Checkstyle 설정 (컨벤션이 안 맞는 경우 노랗게 경고)**
    1. Checkstyle plugins
        - Checkstyle-IDEA (Jamie Shiell) 설치
    2. xml 설정파일 적용
        - Preferences -> Tools -> Checkstyle
        - Configuration File -> + 버튼
        - Use a local Checkstyle file -> Browse -> misc/checkstyle.xml
        - Description은 원하는 대로 입력 후 apply

- **IntelliJ Code Style 설정 (컨벤션 자동 서포트, Reformat Code 기능 시 한번에 컨벤션 적용)
    - Preferences -> Editor -> Code Style -> Scheme 옆 톱니바퀴
    - Import Scheme -> IntelliJ IDEA Code Style XML -> misc/intellij-idea-code-style.xml
    - apply

- **IntelliJ Editor 설정**
    - Preferences -> Editor -> General
    - On Save -> 'Remove trailing blank lines 어쩌구', 'Ensure every saved 어쩌구' 체크
    - apply

- **IntelliJ에서 commit할 때 자동 Reformat Code**
    - Commit -> Amend 옆 톱니바퀴
    - Before commit -> Reformat Code 체크
- **규칙을 무시해야하는 경우 @SuppressWarnings("checkstyle:규칙명") 어노테이션 삽입**
