import React, { useState } from "react";
import styled from "styled-components";
import { Button } from "../Button/Button";
import { Icon } from "components/Common/Icon/Icon";
import { Tag } from "../Tag/Tag";

interface CommentData {
  id: number;
  profile: string;
  author: string;
  createdAt: string;
  contents: string;
  file: string;
}

interface CommentProps {
  comment: CommentData;
}

/* 접속자가 작성자인지 아닌지 추가하기 */
/* 사진 마크다운 관련 추가하기 */
/* 이미지 파일 여러개일 경우 처리 안되있음 */
export const Comment: React.FC<CommentProps> = ({ comment }) => {
  const [isEdit, setIsEdit] = useState(false);
  const [contentValue, setcontentValue] = useState(comment.contents);
  const [imageLoadError, setImageLoadError] = useState(false);

  const handleImageError = () => {
    setImageLoadError(true);
  };

  const handleEditClick = () => {
    setIsEdit(true);
  };

  // const handleSaveClick = () => {
  //   setIsEdit(false);
  // };

  const handleContentChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setcontentValue(e.target.value);
  };

  return (
    <Layout $isEdit={isEdit}>
      <CommentInfoBox>
        <div>
          <img src={comment.profile} alt="" />
          <p>username</p>
          <p>timestamp</p>
        </div>
        <div>
          <Tag
            text="작성자"
            color="##EFF0F6"
            $backgroundColor="#EFF0F6"
            $border
            size="S"
          />
          <Button size="S" variant="ghost" onClick={handleEditClick}>
            <Icon icon="Edit" size="S" />
            편집
          </Button>
          <Button size="S" variant="ghost">
            <Icon icon="Smile" size="S" />
            반응
          </Button>
        </div>
      </CommentInfoBox>
      <CommnentBodyBox $isEdit={isEdit}>
        {isEdit ? (
          <textarea value={contentValue} onChange={handleContentChange} />
        ) : (
          <p>{contentValue}</p>
        )}
        {comment.file && !imageLoadError ? (
          <img src={comment.file} onError={handleImageError} />
        ) : null}
      </CommnentBodyBox>
      <FileButton size="S" variant="ghost">
        <Icon icon="Paperclip" size="S" stroke="nuetralTextDefault" />
        <p>파일 첨부하기</p>
      </FileButton>
    </Layout>
  );
};

const Layout = styled.div<{
  $isEdit: boolean;
}>`
  width: 100%;
  border: ${({ theme: { border } }) => border.default};
  border-radius: ${({ theme }) => theme.radius.large};
  border-color: ${({ $isEdit, theme: { color } }) =>
    $isEdit === false
      ? color.nuetralBorderDefault
      : color.nuetralBorderDefaultActive};
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;

  div:first-child {
    display: flex;
    gap: 8px;
    align-items: center;
  }
  div:last-child {
    display: flex;
    gap: 16px;
    align-items: center;
  }
`;

const CommentInfoBox = styled.div`
  display: flex;
  justify-content: space-between;
  padding: 16px 24px;
  background-color: ${({ theme: { color } }) => color.nuetralSurfaceDefault};
  font: ${({ theme: { font } }) => font.displayM16};
  img {
    width: 32px;
  }

  div > p:nth-child(2) {
    color: ${({ theme: { color } }) => color.nuetralTextDefault};
  }

  div > p:nth-child(3) {
    color: ${({ theme: { color } }) => color.nuetralTextWeak};
  }
`;
const CommnentBodyBox = styled.div<{
  $isEdit: boolean;
}>`
  padding: 16px;
  background-color: ${({ theme: { color } }) => color.nuetralSurfaceStrong};
  height: 100%;

  textarea {
    height: 132px;
    width: 100%;
    border: 0;
    outline: none;
    resize: none;
    background-color: transparent;
    color: ${({ $isEdit, theme: { color } }) =>
      $isEdit === false ? color.nuetralTextWeak : color.nuetralTextStrong};

    &::-webkit-scrollbar {
      width: 5px;
      background-color: transparent;
    }

    &::-webkit-scrollbar-track {
      background-color: transparent;
    }

    &::-webkit-scrollbar-thumb {
      background-color: ${({ theme: { color } }) => color.nuetralTextDefault};
      border-radius: ${({ theme }) => theme.radius.large};
    }

    &::-webkit-scrollbar-button {
      display: none;
    }

    &:not(:focus)::-webkit-scrollbar {
      display: none;
    }
  }

  p {
    color: ${({ theme: { color } }) => color.nuetralTextWeak};
    padding-bottom: 16px;
    white-space: pre-line;
  }
`;

const FileButton = styled(Button)`
  background-color: ${({ theme: { color } }) => color.nuetralSurfaceStrong};
  border-top: ${({ theme: { border } }) => border.dash};
  border-color: ${({ theme: { color } }) => color.nuetralBorderDefault};
  padding: 10px 16px;
  height: 52px;
  display: flex;
  justify-content: flex-start;
`;
