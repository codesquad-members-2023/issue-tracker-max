import { styled } from "styled-components";
import { Button } from "../../components/Button";

type NewIssueFooterProps = {
  invalidTitle: boolean;
  onCancelButtonClick: () => void;
  onSubmitButtonClick: () => void;
};

export function NewIssueFooter({
  invalidTitle,
  onCancelButtonClick,
  onSubmitButtonClick,
}: NewIssueFooterProps) {
  return (
    <Div>
      <Button
        size="M"
        buttonType="Ghost"
        icon="XSquare"
        onClick={onCancelButtonClick}
      >
        작성 취소
      </Button>
      <Button
        size="L"
        buttonType="Container"
        onClick={onSubmitButtonClick}
        disabled={invalidTitle}
      >
        완료
      </Button>
    </Div>
  );
}

const Div = styled.div`
  align-self: flex-end;
  display: flex;
  align-items: center;
  gap: 32px;
`;
