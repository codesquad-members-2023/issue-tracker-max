import { useTheme } from '@emotion/react';
import React, { useState, useEffect } from 'react';
import { ReactComponent as PaperClip } from '@assets/icons/paperclip.svg';
import { Button } from '../Button';
import { TextAreaContainer } from './TextAreaContainer';
import { Caption } from './Caption';
import { AddButtons } from './AddButtons';
import { TextAreaInput } from './TextAreaInput';
import { uploadFile } from 'apis/fileUpload';

type Props = {
  size?: 'defaultSize' | 'S';
  isDisabled?: boolean;
  letterCount?: number;
  textAreaValue: string;
  typeVariant: 'default' | 'add';
  onChangeTextArea: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
  onAppendMarkdownFileUrl: (fileName: string, fileUrl: string) => void;
};

export const TextArea: React.FC<Props> = ({
  size = 'defaultSize',
  isDisabled = false,
  letterCount,
  textAreaValue,
  typeVariant,
  onChangeTextArea,
  onAppendMarkdownFileUrl,
}) => {
  const theme = useTheme() as any;

  const [isDisplayingCount, setIsDisplayingCount] = useState(false);
  const [fileStatus, setFileStatus] =
    useState<DefaultFileStatusType>(initialStatus);

  useEffect(() => {
    if (textAreaValue) {
      setIsDisplayingCount(true);
      const timer = setTimeout(() => setIsDisplayingCount(false), 2000);
      return () => clearTimeout(timer);
    }
  }, [textAreaValue]);

  const initFileStatus = () => {
    setFileStatus(initialStatus);
  };

  const onFileChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
    initFileStatus();

    if (e.target.files && e.target.files.length > 0) {
      const file = e.target.files[0];

      if (!file) {
        setFileStatus((prev) => ({ ...prev, uploadFailed: true }));
        return;
      }

      const fileName = file.name;

      if (file.size > AVAILABLE_FILE_SIZE) {
        setFileStatus((prev) => ({ ...prev, sizeError: true }));
        return;
      }

      if (!file.type.startsWith('image/')) {
        setFileStatus((prev) => ({ ...prev, typeError: true }));
        return;
      }

      try {
        setFileStatus((prev) => ({ ...prev, isUploading: true }));

        const fileUrl = await uploadFile(file);
        onAppendMarkdownFileUrl(fileName, fileUrl.fileUrl);
      } catch {
        setFileStatus((prev) => ({ ...prev, uploadFailed: true }));
      } finally {
        setFileStatus((prev) => ({ ...prev, isUploading: false }));
      }
    }
  };

  const isTyping = textAreaValue.length > 0;

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
          typeVariant={typeVariant}
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

const initialStatus = {
  typeError: false,
  sizeError: false,
  isUploading: false,
  uploadFailed: false,
};

const AVAILABLE_FILE_SIZE = 1048576; //1MB
