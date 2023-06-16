package controllers.definition;

import controllers.dtos.request.Filter;
import controllers.dtos.request.UserInfoRequest;
import controllers.dtos.response.UserConnectionInfoResponse;
import controllers.dtos.response.UserInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Stream;

@RequestMapping("/users")
public interface IUserInfoController {

    @GetMapping
    ResponseEntity<List<UserInfoResponse>> getUsers(@RequestParam("pageIdx") Integer pageIxd, @RequestParam("itemsPerPage") Integer itemsPerPage);

    @PostMapping("/create")
    ResponseEntity<String> create(@RequestBody UserInfoRequest userInfoRequest);

    @PostMapping("/search")
    ResponseEntity<List<UserInfoResponse>> search(@Valid @RequestBody Filter Filter);

    @GetMapping("/{id}/profile")
    ResponseEntity<UserInfoResponse> getUserProfileById(@PathVariable("id") String id);

    @GetMapping("/{id}/non-connected-users")
    ResponseEntity<List<UserConnectionInfoResponse>> getNonConnectedUsers(@PathVariable("id") String id, @RequestParam("pageIdx") Integer pageIxd, @RequestParam(value = "itemsPerPage") Integer itemsPerPage);
}
