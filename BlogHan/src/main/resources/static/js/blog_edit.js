//
const placeLabel = document.getElementById('placeLabel');
const imageUpload = document.querySelector('.image-upload');

//placeLabelがクリックされたときに実行する
placeLabel.addEventListener('click', () => {
    imageUpload.click();
});

//imageUploadの値が変化するときに実行する
imageUpload.addEventListener('change', (event) => {
    //画像を表示するタグを取得
    const imagePreview = document.getElementById('imagePreview');
    //選択した画像ファイルを取得
    const selectedImage = event.target.files[0];

    //もし画像を選んだら
    if (selectedImage) {
        //FileReaderでファイルを読むこみ
        const reader = new FileReader();
        reader.onload = function () {
            //読み込んだ画像ファイルをimagePreview.srcに設定
            imagePreview.src = reader.result;
            //プレビュを表示、アップロードボタンとタグを隠す
            imagePreview.style.display = "block";
            placeLabel.style.display = 'none';
            imageUpload.style.display = 'none';
        };
        //画像ファイルを読み込み終わった後にすぐ更新する
        reader.readAsDataURL(selectedImage);
    }
});

//画像がクリックされたときに実行
document.querySelector('.image-preview').addEventListener('click', () => {
    imageUpload.click();
})