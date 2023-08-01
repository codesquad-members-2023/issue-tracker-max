import { css, useTheme } from '@emotion/react';
import React, { useState } from 'react';
import { ReactComponent as Grip } from '@assets/icons/grip.svg';
import { ReactComponent as PaperClip } from '@assets/icons/paperclip.svg';
import { Button } from './Button';

type Props = {
  size?: 'defaultSize' | 'S';
  isDisabled?: boolean;
  inputType?: React.HTMLInputTypeAttribute;
  letterCount?: number;
  isTyping?: boolean;
  textAreaValue?: string;
  isDisplayingCount?: boolean;
  onTyping: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
  onFileChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
};

export const TextArea: React.FC<Props> = ({
  size = 'defaultSize',
  isDisabled,
  inputType,
  letterCount,
  isTyping,
  textAreaValue,
  isDisplayingCount,
  onTyping,
  onFileChange,
}) => {
  const theme = useTheme() as any;

  //코멘트 영역때문에 사이즈를 더 추가할 수도 있을 것 같습니다
  const SIZE = {
    S: {
      height: '184px',
    },
    defaultSize: {
      height: '552px',
    },
  };

  return (
    <div
      css={{
        width: '912px',
        display: 'flex',
        flexDirection: 'column',
        boxSizing: 'border-box',
        borderRadius: theme.radius.l,
        paddingTop: '16px',
        overflow: 'hidden',
        border: `${theme.border.default} ${theme.neutral.surface.bold}`,
        background: theme.neutral.surface.bold,
        opacity: isDisabled ? theme.opacity.disabled : 1,
        '&:focus-within': {
          background: theme.neutral.surface.strong,
          border: `${theme.border.default} ${theme.neutral.border.defaultActive}`,
        },
        ...SIZE[size],
      }}
    >
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
      <textarea
        value={textAreaValue}
        onChange={onTyping}
        disabled={isDisabled}
        css={{
          flex: '1 0 0',
          padding: '0 16px 16px 16px',
          width: '100%',
          background: 'transparent',
          overflowY: 'auto',
          color: theme.neutral.text.strong,
          font: theme.fonts.displayMedium16,
          caretColor: theme.palette.blue,
          '&::placeholder': {
            color: theme.neutral.text.weak,
            font: theme.fonts.displayMedium16,
          },
        }}
        placeholder="코멘트를 입력하세요"
      />

      <div
        css={{
          borderBottom: `${theme.border.dash} ${theme.neutral.border.default}`,
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'flex-end',
          gap: '8px',
          padding: '8px 16px',
          boxSizing: 'border-box',
          width: '100%',
          height: '52px',
        }}
      >
        {isDisplayingCount && letterCount && (
          <span
            css={{
              color: theme.neutral.text.weak,
              font: theme.fonts.availableMedium12,
            }}
          >
            띄어쓰기 포함 {letterCount}자
          </span>
        )}
        <Grip stroke={theme.neutral.text.weak} />
      </div>

      <div
        css={{
          display: 'flex',
          alignItems: 'center',

          padding: '8px 16px',
          boxSizing: 'border-box',
          width: '100%',
          height: '52px',
        }}
      >
        <label
          htmlFor={inputType}
          css={{
            marginLeft: '-16px',
            background: 'transparent',
            cursor: 'pointer',
            '&:hover': {
              opacity: theme.opacity.hover,
            },
            '&:active': {
              opacity: theme.opacity.press,
            },
          }}
        >
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
        </label>
        <input
          onChange={onFileChange}
          type={inputType}
          name={inputType}
          id={inputType}
          css={{ display: 'none' }}
        />
      </div>
    </div>
  );
};
