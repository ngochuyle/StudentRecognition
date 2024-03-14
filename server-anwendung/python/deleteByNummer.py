import pickle
import sys

def remove_entry(path1, nummer):
    # Đọc file .pkl
    try:
        with open(path1, 'rb') as file:
            data = pickle.load(file)
    except FileNotFoundError:
        return "FILE_NOT_FOUND"
    except Exception as e:
        return "READ_ERROR"

    # Kiểm tra và xóa key 'nummer' nếu tồn tại
    if nummer in data:
        del data[nummer]
    else:
        return "KEY_NOT_FOUND"

    # Ghi lại file .pkl với dictionary còn lại
    try:
        with open(path1, 'wb') as file:
            pickle.dump(data, file)
    except Exception as e:
        return "WRITE_ERROR"

    return "SUCCESS"

# Sử dụng hàm
path1 = sys.argv[1]  # Thay thế bằng đường dẫn thực tế của bạn
nummer = sys.argv[2]  # Thay thế bằng chuỗi ký tự cần xóa
result = remove_entry(path1, nummer)
print(result)