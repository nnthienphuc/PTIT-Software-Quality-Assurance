from selenium import webdriver
from selenium.webdriver.edge.service import Service
from selenium.webdriver.common.by import By
import time

# Đường dẫn Edge WebDriver
EDGE_DRIVER_PATH = "D:\\Download-Applications\\Necessary-Applications\\edgedriver_win64\\msedgedriver.exe"

# Khởi động WebDriver
service = Service(EDGE_DRIVER_PATH)
driver = webdriver.Edge(service=service)

try:
    # Mở trang login
    driver.get("http://localhost:8080/admin/account/login")
    time.sleep(3)

    # Điền thông tin đăng nhập
    driver.find_element(By.ID, "username").send_keys("phucnaoto")
    driver.find_element(By.ID, "password").send_keys("123456")
    time.sleep(2)

    # click btn login
    driver.find_element(By.XPATH, "//input[@type='submit' and @value='Login']").click()
    time.sleep(4)

    # Chuyển đến trang Promotion
    driver.get("http://localhost:8080/admin/promotion")
    time.sleep(3)

    # Nhập từ khóa "Noel" vào ô tìm kiếm
    search_box = driver.find_element(By.ID, "searchInput")
    search_box.clear()
    search_box.send_keys("Năm")
    time.sleep(2)

    # click btn search
    driver.find_element(By.ID, "button-search").click()
    time.sleep(4)

    # Kiểm tra kết quả tìm kiếm
    promotions = driver.find_elements(By.XPATH, "//tbody/tr")
    print("\n--- Kết quả tìm kiếm Năm ---")
    for promo in promotions:
        print(promo.text)

    # Giữ treo 5s
    time.sleep(5)

    # click btn xóa
    clear_button = driver.find_element(By.XPATH, "//button[contains(text(),'Xóa')]")
    clear_button.click()
    time.sleep(3)  # Đợi ô tìm kiếm reset

    # tìm lại ô tìm kiểm (Giải quyết lỗi StaleElementReferenceException)
    search_box = driver.find_element(By.ID, "searchInput")

    # Nhập từ khóa "Học Sinh"
    search_box.send_keys("Học Sinh")
    time.sleep(2)

    # Click btn search
    driver.find_element(By.ID, "button-search").click()
    time.sleep(4)

    # Kiểm tra kết quả tìm kiếm
    promotions = driver.find_elements(By.XPATH, "//tbody/tr")
    print("\n--- Kết quả tìm kiếm 'Học Sinh' ---")
    if not promotions:
        print("Không có dữ liệu khớp với 'Học Sinh' (Đúng như mong đợi)")
    else:
        print("Kết quả không đúng, vẫn có dữ liệu xuất hiện")

    # Giữ edge vẫn mở để quan sát
    print("\n🚀 Test hoàn tất! Trình duyệt sẽ giữ nguyên để quan sát.")
    input("Nhấn Enter để đóng trình duyệt...")

finally:
    driver.quit()
