package tr.gov.tuik.zk.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import tr.gov.tuik.zk.domain.Member;
import tr.gov.tuik.zk.repository.MemberRepository;
import tr.gov.tuik.zk.service.MemberService;
import tr.gov.tuik.zk.test.Hello;

/**
 * Created by Hasan TEKGÜL
 * on 7/9/2015
 */
@VariableResolver(DelegatingVariableResolver.class)
public class MainComposer extends SelectorComposer<Component> {

    @Wire("#labelHello")
    private Label label;
    @Wire("#body")
    private Div body;

    @WireVariable
    private MemberService memberService;
    @WireVariable
    private MemberRepository memberRepository;
    @WireVariable
    private Hello hello;

    private Member member;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        member = memberService.getMemberWithAuthorities();
        if (member != null) {
            label.setValue(member.getFirstName() + " " + member.getLastName());
        }
    }

    @Listen("onClick = #btnHello")
    public void onClickHelloButon() {
        if (member != null && !member.getAuthorities().isEmpty()) {
            label.setValue(hello.hello());
        }
    }
}
