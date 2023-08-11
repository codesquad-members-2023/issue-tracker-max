import { useTheme } from '@emotion/react';
import React, { useState, useEffect } from 'react';
import { ReactComponent as PaperClip } from '@assets/icons/paperclip.svg';
import { Button } from '../Button';
import { TextAreaContainer } from './TextAreaContainer';
import { Caption } from './Caption';
import { AddButtons } from './AddButtons';
import { TextAreaInput } from './TextAreaInput';

type DefaultFileStatusType = {
  typeError: boolean;
  sizeError: boolean;
  isUploading: boolean;
  uploadFailed: boolean;
};

type Props = {
  size?: 'defaultSize' | 'S';
  isDisabled?: boolean;
  letterCount?: number;
  textAreaValue: string;
  onChangeTextArea: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
  onAddFileUrl: (fileName: string, fileUrl: string) => void;
};

export const TextArea: React.FC<Props> = ({
  size = 'defaultSize',
  isDisabled = false,
  letterCount,
  textAreaValue,
  onChangeTextArea,
  onAddFileUrl,
}) => {
  const theme = useTheme() as any;
  const availableFileSize = 1048576; //1MB

  const [isDisplayingCount, setIsDisplayingCount] = useState(false);
  const [fileStatus, setFileStatus] = useState<DefaultFileStatusType>({
    typeError: false,
    sizeError: false,
    isUploading: false,
    uploadFailed: false,
  });

  const isTyping = textAreaValue.length > 0;

  const uploadImage = async (file: File) => {
    try {
      setFileStatus((prev) => ({ ...prev, isUploading: true }));

      const formData = new FormData();
      formData.append('file', file);

      const response = await fetch(
        `${import.meta.env.VITE_APP_BASE_URL}/file-upload`,
        {
          method: 'POST',
          body: formData,
        },
      );

      if (!response.ok) {
        throw new Error('File upload failed');
      }

      const data = await response.json();
      return data;
    } catch (error) {
      setFileStatus((prev) => ({ ...prev, uploadFailed: true }));
    } finally {
      setFileStatus((prev) => ({ ...prev, isUploading: false }));
    }
  };

  const onFileChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
    setFileStatus((prev) => ({
      ...prev,
      sizeError: false,
      typeError: false,
      uploadFailed: false,
    }));

    if (e.target.files && e.target.files.length > 0) {
      const file = e.target.files[0];

      if (!file) {
        setFileStatus((prev) => ({ ...prev, uploadFailed: true }));
        return;
      }

      const fileName = file.name;

      if (file.size > availableFileSize) {
        setFileStatus((prev) => ({ ...prev, sizeError: true }));
        return;
      }

      if (!file.type.startsWith('image/')) {
        setFileStatus((prev) => ({ ...prev, typeError: true }));
        return;
      }

      const fileUrl = await uploadImage(file);

      onAddFileUrl(fileName, fileUrl.fileUrl);
    }
  };

  useEffect(() => {
    if (textAreaValue) {
      setIsDisplayingCount(true);
      const timer = setTimeout(() => setIsDisplayingCount(false), 2000);
      return () => clearTimeout(timer);
    }
  }, [textAreaValue]);

  return (
    <>
      <TextAreaContainer size={size} isDisabled={isDisabled}>
        {isTyping && !isDisabled && (
          <div
            css={{
              padding: '0 16px 8px 16px',
              font: theme.fonts.displayMedium12,
              color: theme.neutral.text.weak,
            }}
          >
            코멘트를 입력하세요
          </div>
        )}
        <TextAreaInput
          textAreaValue={textAreaValue}
          onChangeTextArea={onChangeTextArea}
          isDisabled={isDisabled}
        />

        <Caption
          isDisplayingCount={isDisplayingCount}
          letterCount={letterCount}
        />

        <AddButtons onFileChange={onFileChange} fileStatus={fileStatus}>
          <Button
            typeVariant="ghost"
            size="S"
            css={{
              pointerEvents: 'none',
            }}
          >
            <PaperClip stroke={theme.neutral.text.weak} />
            파일 첨부하기
          </Button>
        </AddButtons>
      </TextAreaContainer>
    </>
  );
};
