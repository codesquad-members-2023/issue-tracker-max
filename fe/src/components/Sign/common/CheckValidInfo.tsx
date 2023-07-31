import { css } from '@emotion/react';
import { color } from '../../../styles/color';
import { font } from '../../../styles/font';

type Variant = 'email' | 'userName' | 'password' | 'doubleCheck';

type Props = {
  variant: Variant;
  valid: boolean;
};

export default function CheckValidInfo({ variant, valid }: Props) {
  return (
    <div css={checkValid}>
      {!valid && variant === 'email'
        ? '올바른 이메일을 입력해주세요'
        : variant === 'userName'
        ? '올바른 닉네임을 입력해주세요'
        : variant === 'password'
        ? '올바른 비밀번호를 입력해주세요'
        : variant === 'doubleCheck'
        ? '비밀번호가 다릅니다'
        : ''}
    </div>
  );
}

const checkValid = css`
  width: 320px;
  height: 20px;
  margin: 5px 0 10px 0;
  padding-left: 16px;
  font: ${font.displayMedium12};
  color: ${color.danger.textDefault};
`;
