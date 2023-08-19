import { Theme, css, useTheme } from '@emotion/react';
import { ReactComponent as PaperclipIcon } from '../../assets/icon/paperclip.svg';
import { ReactComponent as CaptionIcon } from '../../assets/icon/caption.svg';
import { border, font, radius } from '../../styles/styles';
import { customFetch } from '../../util/customFetch';
import { useEffect, useState } from 'react';
import { PostFileRes } from '../../type/Response.type';

interface Props extends React.TextareaHTMLAttributes<HTMLTextAreaElement> {
  height?: string;
  withInfo: boolean;
  onAddImg: (imgMarkdown: string) => void;
}

export default function IssueContent({
  value,
  onChange,
  height = 'auto',
  withInfo,
  onAddImg,
}: Props) {
  const theme = useTheme();
  const [showMessage, setShowMessage] = useState(false);

  const onImageChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
    try {
      if (!e.target.files) {
        return;
      }

      const file = e.target.files[0];
      const formData = new FormData();
      formData.append('multipartFile', file!);

      const response = await customFetch<PostFileRes>({
        method: 'POST',
        subUrl: 'api/comments/upload',
        contentType: 'multipart/form-data',
        body: formData,
      });
      if (response.data) {
        onAddImg(response.data);
      }
      //Memo: 미완성 기능
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    if (value) {
      setShowMessage(true);
      const timer = setTimeout(() => {
        setShowMessage(false);
      }, 2000);

      return () => clearTimeout(timer);
    }
  }, [value]);

  return (
    <div css={content(theme, height, withInfo)}>
      {withInfo && value && <div className="label">코멘트를 입력하세요</div>}
      <textarea
        className="content"
        placeholder="코멘트를 입력하세요"
        value={value}
        onChange={onChange}
      />
      <div className="caption">
        <CaptionIcon />
        {value && showMessage && `띄어쓰기 포함 ${String(value).length}자`}
      </div>
      <label htmlFor="input-file">
        <div className="input-image">
          <PaperclipIcon />
          파일 첨부하기
        </div>
      </label>
      <input
        type="file"
        name="file"
        id="input-file"
        accept="image/svg+xml, image/gif, image/jpeg, image/png, image/bmp, image/webp"
        onChange={onImageChange}
      />
    </div>
  );
}

const content = (theme: Theme, height: string, withInfo: boolean) => css`
  display: flex;
  flex-direction: column;
  width: 100%;
  border-radius: ${radius.large};
  background-color: ${theme.neutral.surfaceBold};

  &:focus-within {
    outline: ${border.default} ${theme.neutral.borderDefaultActive};
    background: ${theme.neutral.surfaceStrong};
  }

  .label {
    padding: 16px 16px 0;
    color: ${theme.neutral.textWeak};
    font: ${font.displayMedium16};
  }

  .content {
    height: ${height};
    padding: 16px 16px 0;
    border: none;
    border-radius: ${getContentRadius(withInfo)};
    background-color: inherit;
    resize: none;
    color: ${theme.neutral.textDefault};
    font: ${font.displayMedium16};

    &::-webkit-scrollbar {
      width: 20px;
    }

    &:focus-within {
      &::-webkit-scrollbar-thumb {
        background-color: ${theme.neutral.textWeak};
        width: 10px;
        border-radius: ${radius.large};
        border: 7px solid ${theme.neutral.surfaceStrong};
      }

      &::-webkit-scrollbar-track {
        background-color: ${theme.neutral.surfaceStrong};
        border-radius: 30px;
      }
    }

    &:focus {
      color: ${theme.neutral.textStrong};
      outline: none;
    }
  }

  .caption {
    display: flex;
    align-items: center;
    flex-direction: row-reverse;
    gap: 8px;
    padding: 0 16px;
    height: 52px;
    border-bottom: ${border.dash} ${theme.neutral.borderDefault};
    color: ${theme.neutral.textWeak};
    font: ${font.availableMedium12};
  }

  .input-image {
    display: flex;
    align-items: center;
    gap: 4px;
    height: 52px;
    padding: 0 16px;
    cursor: pointer;
    border-radius: 0 0 ${radius.large} ${radius.large};
    font: ${font.availableMedium12};
    color: ${theme.neutral.textDefault};
  }

  #input-file {
    display: none;
  }
`;

const WITH_INFO_RADIUS = `${radius.large} ${radius.large} 0 0`;
const WITHOUT_INFO_RADIUS = `none`;
const getContentRadius = (withInfo: boolean) => {
  switch (withInfo) {
    case true:
      return WITH_INFO_RADIUS;
    case false:
      return WITHOUT_INFO_RADIUS;
  }
};
