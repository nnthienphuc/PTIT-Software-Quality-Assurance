# path: NotifyLastPromotionTest.py

from selenium import webdriver
from selenium.webdriver.edge.service import Service
from selenium.webdriver.common.by import By
import time

# Đường dẫn tới Edge WebDriver
EDGE_DRIVER_PATH = "D:\\Download-Applications\\Necessary-Applications\\edgedriver_win64\\msedgedriver.exe"

# Khởi động trình duyệt
service = Service(EDGE_DRIVER_PATH)
driver = webdriver.Edge(service=service)

try:
    # Mở trang đăng nhập
    driver.get("http://localhost:8080/admin/account/login")
    time.sleep(2)

    #  Đăng nhập
    driver.find_element(By.ID, "username").send_keys("phucnaoto")
    driver.find_element(By.ID, "password").send_keys("123456")
    driver.find_element(By.XPATH, "//input[@type='submit' and @value='Login']").click()
    time.sleep(3)

    # Chuyển đến trang khuyến mãi
    driver.get("http://localhost:8080/admin/promotion")
    time.sleep(3)

    # Lấy danh sách các dòng <tr> trong bảng
    rows = driver.find_elements(By.XPATH, "//tbody/tr")
    if not rows:
        print("⚠ Không tìm thấy khuyến mãi nào!")
        driver.quit()
        exit()

    # Lấy dòng cuối cùng
    last_row = rows[-1]

    # Tìm nút "Thông báo" trong dòng cuối
    notify_button = last_row.find_element(By.XPATH, ".//button[contains(text(),'Thông báo')]")

    # Click nút Thông báo
    notify_button.click()
    print("Đã ấn nút Thông báo cho khuyến mãi cuối cùng.")

    # Giữ trình duyệt để quan sát
    input("Nhấn Enter để đóng trình duyệt...")

finally:
    driver.quit()
