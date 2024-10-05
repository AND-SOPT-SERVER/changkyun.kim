package org.sopt;

// Shift을(를) 두 번 눌러 전체 검색 대화상자를 열고 'show whitespaces'를 입력한 다음,
// Enter를 누르세요. 그러면 코드 내에서 공백 문자를 확인할 수 있습니다.
public class Main {
    public static void main(String[] args) {
        // 캐럿을 강조 표시된 텍스트에 놓고 Opt+Enter을(를) 누르면
        // IntelliJ IDEA의 수정 제안을 볼 수 있습니다.
        System.out.printf("Hello and welcome!");

        // Ctrl+R을(를) 누르거나 여백의 녹색 화살표 버튼을 클릭하여 코드를 실행합니다.
        for (int i = 1; i <= 5; i++) {

            // Ctrl+D을(를) 눌러 코드 디버그를 시작합니다. 중단점을 하나 설정해 드렸습니다.
            // 중단점을 더 추가하려면 언제든지 Cmd+F8을(를) 누르세요.
            System.out.println("i = " + i);
        }
    }
}