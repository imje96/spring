package hello.hellospring.serivce;
// 서비스 클래스는 비즈니스에 의존적으로 설계
// repository는 단순히 데이터를 넣고 기계적으로 하는 활동
        import hello.hellospring.domain.Member;
        import hello.hellospring.repository.MemberRepository;
        import hello.hellospring.repository.MemoryMemberRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.List;
        import java.util.Optional;

    public class MemberService {
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
// new 에서 직접 생성하는 것이 아니라 내부에서 바꿔주도록 하는 것
    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //    회원가입
    public Long join(Member member) {
        // 같은 이름이 있는 중복 회원x
        // 파라미터로 넘어온 member.getName
        // optional을 바로 반환하는게 좋지 않음 그래서 optional을 없애도 됨
        //Optional<Member> result = memberRepository.findByName(member.getName());
        validateDuplicateMember(member); //중복 회원 검증
        // 통과하면 저장
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    // result 에 값이 있으면 동작하기
                    //result.ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    // 전체 회원 조회 기능
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
