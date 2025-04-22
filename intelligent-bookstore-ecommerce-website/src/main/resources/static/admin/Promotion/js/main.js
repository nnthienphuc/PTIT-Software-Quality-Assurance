function editPromotion(id) {
    fetch(`/admin/promotion/${id}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('editPromotionId').value = data.promotionId;
            document.getElementById('editPromotionName').value = data.promotionName;
            document.getElementById('editStartDate').value = formatDateTimeForInput(data.startDate);
            document.getElementById('editEndDate').value = formatDateTimeForInput(data.endDate);
            document.getElementById('editCondition').value = data.condition;
            document.getElementById('editDiscountPercent').value = Math.round(data.discountPercent * 100);

            
            var editModal = new bootstrap.Modal(document.getElementById('editModal'));
            editModal.show();
        })
        .catch(error => {
            console.error('Chi tiết lỗi:', error);
            Swal.fire({
                icon: 'error',
                title: 'Lỗi!',
                html: `
                    <p>Có lỗi xảy ra khi lấy thông tin khuyến mãi!</p>
                    <p style="color: red; font-size: 14px;">Chi tiết lỗi: ${error.toString()}</p>
                    <p style="font-size: 12px;">Status: ${error.status || 'N/A'}</p>
                `,
                confirmButtonText: 'Đóng'
            });
        });
}

// Hàm format datetime cho input
function formatDateTimeForInput(date) {
    // Check if the date is a valid string
    const formattedDate = new Date(date);

    if (isNaN(formattedDate.getTime())) {
        return "";  // If invalid date, return empty string
    }

    // Format the date as YYYY-MM-DD
    let year = formattedDate.getFullYear();
    let month = String(formattedDate.getMonth() + 1).padStart(2, '0'); // month is 0-indexed
    let day = String(formattedDate.getDate()).padStart(2, '0');

    return `${year}-${month}-${day}`;
}

// Hàm format datetime cho hiển thị
function formatDateTime(dateTimeString) {
    if (!dateTimeString) return '';
    const date = new Date(dateTimeString);
    return date.toLocaleString('vi-VN', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}

// Hàm format số tiền
function formatCurrency(amount) {
    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(amount);
}
document.getElementById('button-search').addEventListener('click', function() {
    var keyword = document.getElementById('searchInput').value;
    window.location.href = '/admin/promotion?keyword=' + encodeURIComponent(keyword);
});

// Enter key trong input search
document.getElementById('searchInput').addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
        var keyword = this.value;
        window.location.href = '/admin/promotion?keyword=' + encodeURIComponent(keyword);
    }
});
function clearInput() {
       document.getElementById("searchInput").value = ""; // Xóa nội dung ô input
	   window.location.href = '/admin/promotion?keyword=' + encodeURIComponent("");
   }

function deletePromotion(id) {
    // Kiểm tra trạng thái khuyến mãi trước khi xóa
    fetch(`/admin/promotion/${id}`)
        .then(response => response.json())
        .then(promotion => {
            const now = new Date();
            const endDate = new Date(promotion.endDate);
            
            if (now > endDate) {
                Swal.fire({
                    icon: 'error',
                    title: 'Không thể xóa!',
                    text: 'Không thể xóa khuyến mãi đã kết thúc!'
                });
                return;
            }
            
            // Nếu khuyến mãi chưa kết thúc, hiện dialog xác nhận
            Swal.fire({
                title: 'Xác nhận xóa?',
                html: `
                    <div class="text-left">
                        <p>Bạn có chắc chắn muốn xóa khuyến mãi này?</p>
                        <p><strong>Tên khuyến mãi:</strong> ${promotion.promotionName}</p>
                        <p><strong>Thời gian:</strong> ${formatDateTime(promotion.startDate)} - ${formatDateTime(promotion.endDate)}</p>
                        <p><strong>Điều kiện:</strong> ${formatCurrency(promotion.condition)}</p>
                        <p><strong>Giảm giá:</strong> ${promotion.discountPercent}%</p>
                    </div>
                `,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                cancelButtonColor: '#3085d6',
                confirmButtonText: 'Xóa',
                cancelButtonText: 'Hủy',
                customClass: {
                    confirmButton: 'btn btn-danger',
                    cancelButton: 'btn btn-secondary'
                }
            }).then((result) => {
                if (result.isConfirmed) {
                    // Gửi request xóa
                    fetch(`/admin/promotion/delete/${id}`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]')?.content
                        }
                    })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Network response was not ok');
                        }
                        return response.text();
                    })
                    .then(result => {
                        console.log('Delete result:', result);
                        
                        Swal.fire({
                            icon: 'success',
                            title: 'Đã xóa!',
                            text: 'Xóa khuyến mãi thành công!',
                            showConfirmButton: false,
                            timer: 1500
                        }).then(() => {
                            // Refresh data
                            const searchInput = document.getElementById('searchInput');
                            if (searchInput && searchInput.value.trim() !== '') {
                                searchPromotions(); // Refresh search results
                            } else {
                                window.location.reload(); // Reload page if no search
                            }
                        });
                    })
                    .catch(error => {
                        console.error('Delete error:', error);
                        Swal.fire({
                            icon: 'error',
                            title: 'Lỗi!',
                            html: `
                                <p>Không thể xóa khuyến mãi!</p>
                                <p style="color: red; font-size: 14px;">Chi tiết lỗi: ${error.message}</p>
                            `,
                            confirmButtonText: 'Đóng'
                        });
                    });
                }
            });
        })
        .catch(error => {
            console.error('Error fetching promotion details:', error);
            Swal.fire({
                icon: 'error',
                title: 'Lỗi!',
                text: 'Không thể lấy thông tin khuyến mãi!'
            });
        });
}

function notificationPromotion(id) {
    fetch(`/admin/promotion/notification/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]')?.content
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Lỗi khi gọi API");
        }
        return response.text(); // vì Controller trả về chuỗi
    })
    .then(message => {
        Swal.fire({
            icon: 'success',
            title: 'Thông báo',
            text: message
        });
    })
    .catch(error => {
        console.error(error);
        Swal.fire({
            icon: 'error',
            title: 'Lỗi!',
            text: 'Không thể lấy thông tin từ máy chủ.'
        });
    });
}


// Hàm hỗ trợ format datetime (nếu chưa có)
function formatDateTime(dateTimeString) {
    if (!dateTimeString) return '';
    const date = new Date(dateTimeString);
    return date.toLocaleString('vi-VN', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}

// Hàm hỗ trợ format currency (nếu chưa có)
function formatCurrency(amount) {
    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(amount);
}
// Hàm format datetime
function formatDateTime(dateTimeString) {
    if (!dateTimeString) return '';
    const date = new Date(dateTimeString);
    return date.toLocaleString('vi-VN', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}

// Hàm format currency
function formatCurrency(amount) {
    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(amount);
}

// Thêm event listeners
document.addEventListener('DOMContentLoaded', function() {
    const searchButton = document.getElementById('button-search');
    const searchInput = document.getElementById('searchInput');
    
    if (searchButton) {
        searchButton.addEventListener('click', function() {
            searchPromotions();
        });
    }
    
    if (searchInput) {
        searchInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                searchPromotions();
            }
        });

        // Thêm debounce để tránh gọi API quá nhiều
        let timeoutId;
        searchInput.addEventListener('input', function() {
            clearTimeout(timeoutId);
            timeoutId = setTimeout(() => {
                if (this.value.trim() !== '') {
                    searchPromotions();
                }
            }, 500);
        });
    }
});
// Validate form trước khi submit
function validatePromotionForm(formId) {
    const form = document.getElementById(formId);
    const startDate = new Date(form.querySelector('[name="startDate"]').value);
    const endDate = new Date(form.querySelector('[name="endDate"]').value);
    const condition = parseFloat(form.querySelector('[name="condition"]').value);
    const discountPercent = parseFloat(form.querySelector('[name="discountPercent"]').value);

    if (endDate <= startDate) {
        Swal.fire({
            icon: 'error',
            title: 'Lỗi!',
            text: 'Ngày kết thúc phải sau ngày bắt đầu!'
        });
        return false;
    }

    if (condition < 0) {
        Swal.fire({
            icon: 'error',
            title: 'Lỗi!',
            text: 'Điều kiện phải là số dương!'
        });
        return false;
    }

    if (discountPercent < 0 || discountPercent > 100) {
        Swal.fire({
            icon: 'error',
            title: 'Lỗi!',
            text: 'Phần trăm giảm giá phải từ 0 đến 100!'
        });
        return false;
    }

    return true;
}

// Thêm event listeners cho form submit
document.addEventListener('DOMContentLoaded', function() {
    const addForm = document.querySelector('#addModal form');
    const editForm = document.querySelector('#editModal form');

    if (addForm) {
        addForm.addEventListener('submit', function(e) {
            if (!validatePromotionForm('addModal')) {
                e.preventDefault();
            }
        });
    }

    if (editForm) {
        editForm.addEventListener('submit', function(e) {
            if (!validatePromotionForm('editModal')) {
                e.preventDefault();
            }
        });
    }

    // ... existing event listeners ...
});