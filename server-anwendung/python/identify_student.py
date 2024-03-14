import torch
import os
from facenet_pytorch import MTCNN, InceptionResnetV1
from PIL import Image
import pickle
from torchvision import transforms
import sys
sys.stdout.reconfigure(encoding='utf-8')

def load_embeddings(pklPath):
    # Load embeddings dictionary từ file .pkl
    with open(pklPath, 'rb') as f:
        return pickle.load(f)
def euclidean_distance(tensor1, tensor2):
    return torch.norm(tensor1 - tensor2, p=2)
def extract_embedding(image_path, mtcnn, resnet):
    img = Image.open(image_path).convert('RGB')

    # Phát hiện khuôn mặt và trích xuất embedding
    img_cropped = mtcnn(img)
    if img_cropped is not None:
        if not isinstance(img_cropped, torch.Tensor):
            img_cropped = img_cropped[0]  # Nếu có nhiều khuôn mặt, chỉ lấy khuôn mặt đầu tiên

        with torch.no_grad():
            embedding = resnet(img_cropped)  # Thêm chiều batch
        return embedding
    else:
        return None

def find_closest_match(test_embedding, embeddings_dict, threshold=0.6):
    closest_studentID = None
    closest_distance = float('inf')
    for studentID, embeddings in embeddings_dict.items():
        print("Huy1")
    for studentID, embeddings in embeddings_dict.items():
        print("Huy")
        for embedding in embeddings:
    # Sử dụng khoảng cách Euclidean (L2)

            distance = torch.dist(test_embedding, embedding, p=2).item()
            print(f"Khoảng cách từ ảnh đầu vào đến {studentID}: {distance}")

            if distance < closest_distance:
                closest_distance = distance
                closest_studentID = studentID

    if closest_distance < threshold:
        return closest_studentID
    else:
        return None

def identify_student(image_path, pklPath):
    # Khởi tạo mô hình
    mtcnn = MTCNN(keep_all=True)
    resnet = InceptionResnetV1(pretrained='vggface2',num_classes=2).eval()

    # Load embeddings từ file .pkl
    embeddings_dict = load_embeddings(pklPath)

    # Trích xuất embedding cho ảnh đầu vào
    test_embedding = extract_embedding(image_path, mtcnn, resnet)
    if test_embedding is not None:
        # Tìm mã học sinh khớp nhất
        studentID = find_closest_match(test_embedding, embeddings_dict)
        return studentID
    else:
        print("Không thể phát hiện khuôn mặt trong ảnh.")
        return None

# Sử dụng hàm

image_path = sys.argv[1]  # Đường dẫn đến ảnh bạn muốn nhận diện
pklPath = sys.argv[2]  # Đường dẫn đến file .pkl chứa embeddings
studentID = identify_student(image_path, pklPath)
print(studentID)
