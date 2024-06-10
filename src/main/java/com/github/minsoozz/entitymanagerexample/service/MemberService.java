package com.github.minsoozz.entitymanagerexample.service;

import com.github.minsoozz.entitymanagerexample.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MemberService {

    private final EntityManager entityManager;

    public MemberService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Member> execute() {
        String jpql = generateJPQL();
        TypedQuery<Member> query = entityManager.createQuery(jpql, Member.class);
        setQueryParameters(query);
        List<Member> result = query.getResultList();
        return result;
    }

    private void setQueryParameters(TypedQuery<Member> query) {
        List<Member> members = getMembers();
        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            query.setParameter("id" + i, member.id());
            query.setParameter("name" + i, member.name());
            query.setParameter("startDate" + i, member.createDate());
            query.setParameter("endDate" + i, member.createDate());
        }
    }

    private String generateJPQL() {
        StringBuilder jpql = new StringBuilder();
        jpql.append("select m from MemberEntity m where ");
        List<Member> members = getMembers();
        for (int i = 0; i < members.size(); i++) {
            if (i > 0) {
                jpql.append(" OR ");
            }
            jpql.append("(m.id = :id").append(i)
                    .append(" AND m.name = :name").append(i)
                    .append(" AND m.createdTime BETWEEN :startDate").append(i)
                    .append(" AND :endDate").append(i)
                    .append(")");
        }
        return jpql.toString();
    }

    private List<Member> getMembers() {
        return List.of(
                new Member(1L, "a", LocalDateTime.now()),
                new Member(1L, "a", LocalDateTime.now()),
                new Member(1L, "a", LocalDateTime.now())
        );
    }
}
