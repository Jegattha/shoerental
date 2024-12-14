package ch.zhaw.shoerental.service;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
@Service
public class MieterService {
 public String getEmail() {
 Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 return jwt.getClaimAsString("email");
 }
}