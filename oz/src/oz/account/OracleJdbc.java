package oz.account;

public interface OracleJdbc {
    
    // Oracle DB에 접속하는 주소(, 포트번호, SID)
    String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    
    // Oracle DB에 접속하는 계정
    String USER = "oz";
    
    // Oracle DB에 접속하는 계정 비밀번호
    String PASSWORD = "00";

}

