import os
import torch
from facenet_pytorch import MTCNN, InceptionResnetV1
from PIL import Image
import pickle
import sys
from torchvision import transforms

# Khởi tạo
mtcnn = MTCNN(keep_all=True)
resnet = InceptionResnetV1(pretrained='vggface2',num_classes=2).eval()  # Sử dụng mô hình đã được đào tạo sẵn

# Trích xuâất và lưu pkl
def extract_and_save_embeddings(imgFolder, pklPath, studentID):
    # Load existing data if available
    if os.path.exists(pklPath):
        with open(pklPath, 'rb') as f:
            embeddings_dict = pickle.load(f)
    else:
        embeddings_dict = {}

    # Thêm embeddings cho mã học sinh mới
    student_embeddings = []

    for img_name in os.listdir(imgFolder):
        img_path = os.path.join(imgFolder, img_name)
        img = Image.open(img_path).convert('RGB')

        # Phát hiện khuôn mặt và trích xuất embeddings
        img_cropped = mtcnn(img)

        if img_cropped is not None:
            if not isinstance(img_cropped, torch.Tensor):
                img_cropped = img_cropped[0]  # Nếu có nhiều khuôn mặt, chỉ lấy khuôn mặt đầu tiên

            with torch.no_grad():

                embedding = resnet(img_cropped)  # Thêm chiều batch
            student_embeddings.append(embedding)

    # Lưu hoặc cập nhật embeddings cho sinh viên vào dictionary
    if studentID in embeddings_dict:
        embeddings_dict[studentID] += student_embeddings
    else:
        embeddings_dict[studentID] = student_embeddings

    # Lưu embeddings vào file .pkl
    with open(pklPath, 'wb') as f:
        pickle.dump(embeddings_dict, f)

    print(f"Đã lưu embeddings vào {pklPath}")


# chạy
#imgFolder = 'E:/HTW/Student/79549/OriginalImages'
#pklPath = 'E:/HTW/Student/Recognizer/embeddings.pkl'
#studentID = '79549'
imgFolder= sys.argv[1]
pklPath = sys.argv[2]
studentID = sys.argv[3]
print("Huy")
# Gọi hàm để trích xuất và lưu embeddings
extract_and_save_embeddings(imgFolder, pklPath,studentID)



