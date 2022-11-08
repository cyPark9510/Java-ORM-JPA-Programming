package study.datajpa.repository;

public interface UsernameOnly {

    // Open Projections
//    @Value("#{target.username + ' ' + target.age}")
    String getUsername();
}
