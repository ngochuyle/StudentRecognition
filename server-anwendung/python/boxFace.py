import torch
from facenet_pytorch import MTCNN
from PIL import Image, ImageDraw

mtcnn = MTCNN(keep_all=True)
def draw_faces(image_path):
    # Mở ảnh và chuyển đổi sang RGB
    img = Image.open(image_path).convert('RGB')

    # Phát hiện khuôn mặt trong ảnh
    boxes, _ = mtcnn.detect(img)

    # Vẽ viền quanh khuôn mặt
    draw = ImageDraw.Draw(img)
    if boxes is not None:
        for box in boxes:
            print(f"Khuôn mặt được phát hiện có tọa độ: {box}")
            draw.rectangle(box.tolist(), outline=(255, 0, 0), width=4)

    # Hiển thị ảnh
    img.show()

# Đường dẫn đến ảnh bạn muốn phân tích
image_path = 'E:/HTW/Student/79548/OriginalImages/79548_original_3.jpg'
draw_faces(image_path)