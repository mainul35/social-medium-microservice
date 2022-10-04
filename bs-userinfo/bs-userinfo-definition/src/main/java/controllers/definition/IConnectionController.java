package controllers.definition;

import controllers.dtos.response.UserConnectionInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RequestMapping("/users/{userId}/connections")
public interface IConnectionController {

    @PostMapping("/request/{connectionId}")
    ResponseEntity<UserConnectionInfoResponse> requestConnection(@PathVariable("userId") String userId, @PathVariable("connectionId") String connectionId);

    @PutMapping("/accept/{connectionId}")
    ResponseEntity<UserConnectionInfoResponse> acceptConnection(@PathVariable("userId") String userId, @PathVariable("connectionId") String connectionId);

    @PutMapping("/reject/{connectionId}")
    ResponseEntity<UserConnectionInfoResponse> rejectConnection(@PathVariable("userId") String userId, @PathVariable("connectionId") String connectionId);

    @PutMapping("/block/{connectionId}")
    ResponseEntity<UserConnectionInfoResponse> blockConnection(@PathVariable("userId") String userId, @PathVariable("connectionId") String connectionId);

    @PutMapping("/unblock/{connectionId}")
    ResponseEntity<UserConnectionInfoResponse> unblockConnection(@PathVariable("userId") String userId, @PathVariable("connectionId") String connectionId);

    @GetMapping("/requests")
    ResponseEntity<Stream<UserConnectionInfoResponse>> getConnectionRequests(@PathVariable(name = "userId", required = false) String userId, @RequestParam("pageIdx") Integer pageIxd, @RequestParam(value = "itemsPerPage") Integer itemsPerPage);

    @GetMapping("/blocked")
    ResponseEntity<Stream<UserConnectionInfoResponse>> getBlockedConnections(@PathVariable(name = "userId", required = false) String userId, @RequestParam("pageIdx") Integer pageIxd, @RequestParam("itemsPerPage") Integer itemsPerPage);

    @GetMapping
    ResponseEntity<Stream<UserConnectionInfoResponse>> getConnectedUsers(@PathVariable(name = "userId", required = false) String userId, @RequestParam("pageIdx") Integer pageIxd, @RequestParam("itemsPerPage") Integer itemsPerPage);
}
