import paperClipIcon from "@assets/icon/paperclip.svg";
import { postImage } from "api";
import React from "react";
import styled from "styled-components";
import { Label } from "../Label.style";

export default function FileUploadArea({
  appendContent,
}: {
  appendContent: (content: string) => void;
}) {
  const onFileInputChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
    try {
      if (!e.target.files) return;
      const file = e.target.files[0];
      const filename = file.name;

      const {
        data: { fileUrl },
      } = await postImage(file);

      if (fileUrl) {
        appendContent(`![${filename}](${fileUrl})`);
      }
    } catch (error) {
      // TODO: error handling
      console.log(error);
    }
  };

  return (
    <StyledFileUploadArea>
      <Label className="file-upload-label" htmlFor="file-upload-input">
        <img
          className="file-upload-icon"
          src={paperClipIcon}
          alt="파일 첨부 아이콘"
        />
        파일 첨부하기
        <input
          type="file"
          id="file-upload-input"
          onChange={onFileInputChange}
        />
      </Label>
    </StyledFileUploadArea>
  );
}

const StyledFileUploadArea = styled.div`
  width: 100%;
  height: 52px;
  display: flex;
  padding: 0 16px;

  .file-upload-label {
    width: 100%;
    gap: 4px;
  }

  #file-upload-input {
    display: none;
  }

  .file-upload-icon {
    width: 16px;
    height: 16px;
    filter: ${({ theme: { filter } }) => filter.neutralTextDefault};
  }

  :hover {
    cursor: pointer;
  }
`;
