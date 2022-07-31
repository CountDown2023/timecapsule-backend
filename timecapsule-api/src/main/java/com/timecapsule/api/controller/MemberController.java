package com.timecapsule.api.controller;


import com.timecapsule.api.dto.SignupRequest;
import com.timecapsule.api.service.MemberService;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignupRequest request) {
        memberService.signUp(request.getUsername(), request.getPassword(), request.getEmail());
        return ResponseEntity.created(URI.create("/api/login")).build();
    }
}
