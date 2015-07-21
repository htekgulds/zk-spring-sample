package tr.gov.tuik.zk.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import tr.gov.tuik.zk.repository.MemberRepository;
import tr.gov.tuik.zk.service.MemberService;
import tr.gov.tuik.zk.validator.MemberValidator;

/**
 * Created by Hasan TEKGÜL
 * on 7/13/2015
 */
@VariableResolver(DelegatingVariableResolver.class)
public class LoginComposer extends SelectorComposer<Component> {

    @Wire
    private Textbox tbUsername;
    @Wire
    private Textbox tbPassword;
    @Wire
    private Label labelLoginError;

    @WireVariable
    private MemberService memberService;
    @WireVariable
    private MemberRepository memberRepository;
    @WireVariable
    private MemberValidator memberValidator;

//    @Listen("onClick = #btnLogin")
//    public void onClickLoginButton() {
//        String username = tbUsername.getValue();
//        String password = tbPassword.getValue();
//    }

}
