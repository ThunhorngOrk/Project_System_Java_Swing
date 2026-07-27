package library_management_system.controller;

import library_management_system.dto.ApiResponse;
import library_management_system.dto.BorrowRequest;
import library_management_system.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/borrows")
@CrossOrigin(origins = "*")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @PostMapping("/borrow")
    public ResponseEntity<?> borrowBook(@RequestBody BorrowRequest request) {
        String result = borrowService.borrowBook(request.getUsername(), request.getBookTitle());
        if ("SUCCESS".equals(result)) {
            return ResponseEntity.ok(ApiResponse.success("Book borrowed successfully"));
        }
        return ResponseEntity.ok(ApiResponse.error(result));
    }

    @PostMapping("/return")
    public ResponseEntity<ApiResponse> returnBook(@RequestBody BorrowRequest request) {
        boolean success = borrowService.returnBook(request.getUsername(), request.getBookTitle());
        if (success) {
            return ResponseEntity.ok(ApiResponse.success("Book returned successfully"));
        }
        return ResponseEntity.ok(ApiResponse.error("No active borrow found"));
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllActiveBorrows() {
        return ResponseEntity.ok(borrowService.getAllActiveBorrows());
    }
}
