# 미래에 사용할수 있도록 샘플로 추가만 해두었습니다

# 베이스 이미지 선택
FROM openjdk:17-jdk-slim

# JAR 파일을 컨테이너에 복사
COPY build/libs/remittance-server-0.0.1-SNAPSHOT.jar /app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]